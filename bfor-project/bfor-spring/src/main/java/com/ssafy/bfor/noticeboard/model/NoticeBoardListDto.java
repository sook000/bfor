package com.ssafy.bfor.noticeboard.model;

import java.util.List;

import com.ssafy.bfor.freeboard.model.FreeBoardDto;

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
public class NoticeBoardListDto {

	
	private List<NoticeBoardDto> articles;
	private int currentPage;
	private int totalPageCount;
}
