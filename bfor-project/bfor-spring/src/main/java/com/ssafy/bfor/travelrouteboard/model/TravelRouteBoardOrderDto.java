package com.ssafy.bfor.travelrouteboard.model;

import java.util.List;

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
public class TravelRouteBoardOrderDto {
	private int trbNo;
	private int contentId;
	private int order;
	private String memo;
}
