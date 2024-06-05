package com.ssafy.bfor.travelrouteboard.model;

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
public class TravelRouteBoardDto {
	private int trbNo;
	private String userId;
	private String title;
	private String content;
	private String startdate;
	private String enddate;
	private int favoriteCount;
	private int hit;
	private String regdate;
	private String fixdate;
	private int totalDistance;
	private int hashTagCode;
	private String first_image;
}
