package com.ssafy.bfor.freeboard.model;

import java.sql.Timestamp;

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
public class FreeBoardCommentDto {
	private int cmtNo;
	private int fbNo;
	private String userId;
	private String cmtContent;
	private String regdate;
}
