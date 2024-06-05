package com.ssafy.bfor.freeboard.model;

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
public class FreeBoardListDto {
	

	private List<FreeBoardDto> articles;
	private int currentPage;
	private int totalPageCount;

}
