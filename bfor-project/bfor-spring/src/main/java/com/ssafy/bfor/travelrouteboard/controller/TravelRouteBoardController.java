package com.ssafy.bfor.travelrouteboard.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bfor.travelinfo.model.TravelInfoDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardListDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardThumbnailDto;
import com.ssafy.bfor.travelrouteboard.service.TravelRouteBoardService;

@RestController
@RequestMapping("/travelrouteboard")
//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
public class TravelRouteBoardController {
	
	TravelRouteBoardService travelRouteBoardService;
	
	@Autowired
	public TravelRouteBoardController(TravelRouteBoardService travelRouteBoardService) {
		this.travelRouteBoardService = travelRouteBoardService;
	}
	
	@PostMapping("/insert")
	public ResponseEntity<?> insert(@RequestBody HashMap<String, Object> map) {
		try {
			return new ResponseEntity<>(travelRouteBoardService.insert(map), HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	@GetMapping("/{trbNo}")
	public ResponseEntity<?> read(@PathVariable int trbNo) {
		try {
			return new ResponseEntity<TravelRouteBoardDto>(travelRouteBoardService.read(trbNo), HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/getMaxTrbId")
	public ResponseEntity<?> getMaxTrbId(@RequestParam String userId) {
		try {
				
			return new ResponseEntity<Integer>(travelRouteBoardService.getMaxTrbId(userId), HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/getList")
	public ResponseEntity<?> getList(@RequestParam Map<String, String> map) {
		try {
			// 내 찜 목록 확인(리스트 int)
			// 유저 아이디 가져오기(리스트, String)
			Map<String, Object> param = new HashMap<String, Object>();
			int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
			int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
			int start = currentPage * sizePerPage - sizePerPage;
//			System.out.println(travelRouteBoardService.getList(map));
			return new ResponseEntity<TravelRouteBoardListDto>(travelRouteBoardService.getList(map), HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PostMapping("/getNormalDistance")
	public ResponseEntity<?> getNormalDistance(@RequestBody List<TravelInfoDto> list) {
		try {
			return new ResponseEntity<Integer>(travelRouteBoardService.getNormalDistance(list), HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PostMapping("/getDijkstraDistance")
	public ResponseEntity<?> getDijkstraDistance(@RequestBody List<TravelInfoDto> list) {
		try {
			return new ResponseEntity<Integer>(travelRouteBoardService.getDijkstraDistance(list), HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	@PostMapping("/getDijkstraDistanceList")
	public ResponseEntity<?> getDijkstraDistanceList(@RequestBody List<TravelInfoDto> list) {
		try {
			System.out.println(list);
			return new ResponseEntity<List<TravelInfoDto>>(travelRouteBoardService.getDijkstraDistanceList(list), HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}

	@PutMapping("/")
	public ResponseEntity<?> update(@RequestBody HashMap<String, String> map) {
		try {
			return new ResponseEntity<Integer>(travelRouteBoardService.update(map), HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/{trbNo}")
	public ResponseEntity<?> delete(@PathVariable int trbNo) {
		try {
			return new ResponseEntity<Integer>(travelRouteBoardService.delete(trbNo), HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/getOrderInfo/{trbNo}")
	public ResponseEntity<?> getOrderInfo(@PathVariable int trbNo) {
		return new ResponseEntity<List<TravelInfoDto>> (travelRouteBoardService.getOrderInfo(trbNo), HttpStatus.OK);
	}
	
	// 입력 값은 TravelRouteBoardListDto
	@GetMapping("/getThumbnailList")
	public ResponseEntity<?> getThumbnailList(@RequestBody HashMap<String, String> map) {
		try {
			System.out.println(map);
			return new ResponseEntity<List<TravelRouteBoardThumbnailDto>>(travelRouteBoardService.getThumbnailList(map), HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
