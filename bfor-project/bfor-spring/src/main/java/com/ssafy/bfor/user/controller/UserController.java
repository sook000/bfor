package com.ssafy.bfor.user.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.bfor.user.model.FileInfoDto;
import com.ssafy.bfor.user.model.GugunDto;
import com.ssafy.bfor.user.model.SidoDto;
import com.ssafy.bfor.user.model.UserDto;
import com.ssafy.bfor.user.model.service.UserService;
import com.ssafy.bfor.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {

	private final UserService userService;
	private final JWTUtil jwtUtil;
	
	@Value("${file.upload-dir}")
    private String uploadDir;
	
	public UserController(UserService userService, JWTUtil jwtUtil) {
		super();
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
//	아이디 중복 체크
	@GetMapping("/idCheck/{userId}")
	public ResponseEntity<Integer> checkId(@PathVariable String userId) throws Exception {
		System.out.println("checkId호출: " + userId);
		return new ResponseEntity<Integer>(userService.checkId(userId), HttpStatus.OK);
	}
	
//	시도 리스트 받기
	@GetMapping("/getSido")
	public ResponseEntity<List<SidoDto>> getSido() throws Exception {
		return new ResponseEntity<List<SidoDto>>(userService.getSido(), HttpStatus.OK);
	}
	
//	구군리스트 받기
	@GetMapping("/getGugun/{sidoCode}")
	public ResponseEntity<List<GugunDto>> getGugun(@PathVariable int sidoCode) throws Exception {
		return new ResponseEntity<List<GugunDto>>(userService.getGugun(sidoCode), HttpStatus.OK);
	}
	
////	회원 가입하기
//	@PostMapping("/register")
//	public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
//		System.out.println(userDto.toString());
//		try {
//			userService.registerUser(userDto);
//			return new ResponseEntity<Void>(HttpStatus.CREATED);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
	
	//회원 가입하기
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestPart(name="userDto") UserDto userDto, @RequestPart(name="profileImage", required = false) MultipartFile file) throws Exception{
		System.out.println(userDto.toString());
		System.out.println(file.toString());
		if (file != null && !file.isEmpty()) {
			System.out.println("file존재합니다");
//			String realPath = servletContext.getRealPath("/upload");
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadDir + File.separator + today;
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
			
			FileInfoDto fileInfoDto = new FileInfoDto();
			String originalFileName = file.getOriginalFilename();
			if (originalFileName != null && !originalFileName.isEmpty()) {
				String saveFileName = UUID.randomUUID().toString()
						+ originalFileName.substring(originalFileName.lastIndexOf('.'));
				fileInfoDto.setSaveFolder(today);
				fileInfoDto.setOriginalFile(originalFileName);
				fileInfoDto.setSaveFile(saveFileName);
				file.transferTo(new File(folder, saveFileName));
			}	
		
			userDto.setProfileImage(fileInfoDto);
			System.out.println(userDto.getProfileImage().toString());
		}
		try {
			userService.registerUser(userDto);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
//	아이디와 비밀번호 이용한 로그인 처리
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto userDto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			UserDto loginUser = userService.login(userDto);
			if (loginUser != null) {
				String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
				
//				발급받은 refresh token 을 DB에 저장.
				userService.saveRefreshToken(loginUser.getUserId(), refreshToken);
				
//				JSON 으로 token 전달.
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				
				status = HttpStatus.CREATED;
			} else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해 주세요.");
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
//	회원 정보를 담은 토큰을 반환함
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("userId") String userId, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) { // 사용 가능한 토큰
			try {
				UserDto userDto = userService.userInfo(userId);
				resultMap.put("userInfo", userDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else { // 사용 불가능 토큰
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
//	만료된 access token 재발급
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		if (jwtUtil.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(userDto.getUserId()))) {
				String accessToken = jwtUtil.createAccessToken(userDto.getUserId());
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else { // refresh token도 사용 불가
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	} 
	
//	회원 정보를 담은 토큰을 제거한다
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable ("userId") String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleteRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) { // 로그아웃 실패
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
//	이름과 전화번호로 ID 찾기
	@GetMapping("/findId")
	public ResponseEntity<String> findUserId(@RequestParam Map<String, String> map) throws Exception {
		System.out.println(map.toString());
		String string = userService.findUserId(map);
		if (string == null || string.length() == 0) {
			//notFound로 처리
			return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
 		} else {
 			return new ResponseEntity<String>(string, HttpStatus.OK);
 		}
	}
	
//	ID, 이름, 전화번호로 ID 찾기
	@GetMapping("/authUser")
	public ResponseEntity<String> authUser(@RequestParam Map<String, String> map) throws Exception {
		String string = userService.authUser(map);
		if(string == null || string.length() == 0) {
			return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<String>(string, HttpStatus.OK);		}
	}	
	
//	id,이름,전화번호 입력해 인증된 사용자 비밀번호 재설정
	@PutMapping
	public ResponseEntity<String> modifyPwd(@RequestBody UserDto userDto) throws Exception{
		userService.modifyPwd(userDto);
		return new ResponseEntity<String>("good", HttpStatus.OK);
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
