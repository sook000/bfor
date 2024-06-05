package com.ssafy.bfor.mypage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bfor.freeboard.model.FreeBoardDto;
import com.ssafy.bfor.mypage.model.service.MyPageService;
import com.ssafy.bfor.travelinfo.model.TravelInfoDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardDto;
import com.ssafy.bfor.user.model.UserDto;

@RestController
@RequestMapping("/mypage")
public class MyPageController {

	private MyPageService myPageService;

	@Autowired
	public MyPageController(MyPageService myPageService) {
		super();
		this.myPageService = myPageService;
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<FreeBoardDto>> listFreeArticle(@PathVariable String userId) throws Exception {
		return new ResponseEntity<List<FreeBoardDto>>(myPageService.listFreeArticle(userId), HttpStatus.OK);
	}
	
	@GetMapping("/sido")
	public ResponseEntity<?> getSidoGugun(@RequestParam Map<String, String> map) throws Exception {
		return new ResponseEntity<Map<String, String>>(myPageService.getSidoGugun(map), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String> modifyUser(@RequestBody UserDto userDto) throws Exception {
		myPageService.modifyUser(userDto);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteUser(@RequestParam Map<String, String> map) throws Exception{
		UserDto userDto = new UserDto();
		userDto.setUserId(map.get("userId"));
		userDto.setUserPassword(map.get("userPassword"));
		System.out.println(userDto.toString());
		if (myPageService.deleteUser(userDto)) {
			return ResponseEntity.ok().build();
		} else {
			System.out.println("비밀번호 매칭 실패");
//			일단 not found 처리하긴 함
			return new ResponseEntity<String>("error", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/zzimList/{userId}")
	public ResponseEntity<List<FreeBoardDto>> listZzim(@PathVariable String userId) throws Exception {
		return new ResponseEntity<List<FreeBoardDto>>(myPageService.listZzim(userId), HttpStatus.OK);
	}
	
	@GetMapping("/listroute/{userId}")
	public ResponseEntity<List<TravelRouteBoardDto>> listroute(@PathVariable String userId) throws Exception {
		List<TravelRouteBoardDto> list = myPageService.listroute(userId);
		System.out.println(list.toString());
		return new ResponseEntity<List<TravelRouteBoardDto>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/like")
	public ResponseEntity<?> calcLike(@RequestParam Map<String, String> map) throws Exception {
		return new ResponseEntity<Double>(myPageService.calcLike(map), HttpStatus.OK);
	}
}
