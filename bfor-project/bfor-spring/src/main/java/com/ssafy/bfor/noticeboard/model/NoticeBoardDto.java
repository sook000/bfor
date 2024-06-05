package com.ssafy.bfor.noticeboard.model;

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
public class NoticeBoardDto {
	private int noticeNo;
	private String adminId;
	private String title;
	private String noticeContent;
	private String regdate;
	
}
