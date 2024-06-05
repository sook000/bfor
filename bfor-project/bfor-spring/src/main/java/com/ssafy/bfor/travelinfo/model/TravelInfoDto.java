package com.ssafy.bfor.travelinfo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TravelInfoDto {
	private int contentId;
	private int contentTypeId;
	private String title;
	private String addr1;
	private String addr2;
	private Double latitude; // 위도
	private Double longitude; // 경도
	private String zipCode;
	private String tel;
	private String mlevel;
	private String firstImage;
	private String firstImage2;
	private int favoritCnt;
	private int sidoCode;
	private int gugunCode;
	private String overview;
	private String homepage;
}
