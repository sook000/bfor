package com.ssafy.bfor.recommend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bfor.recommend.model.TravelInfoFavoriteDto;
import com.ssafy.bfor.recommend.model.service.RecommendService;


@RestController
@RequestMapping("/recommend")
public class RecommendController {
	
	private RecommendService recommendService;

	public RecommendController(RecommendService recommendService) {
		super();
		this.recommendService = recommendService;
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<TravelInfoFavoriteDto> getRecommendList(@PathVariable String userId) throws Exception {
		return new ResponseEntity<>(recommendService.listPlaces(userId), HttpStatus.OK);
	}
	
}
