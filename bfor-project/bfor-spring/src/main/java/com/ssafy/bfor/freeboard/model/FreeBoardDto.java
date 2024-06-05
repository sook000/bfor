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
public class FreeBoardDto {
	private int fbNo;
	private String userId;
	private String title;
	private String content;
	private String regdate;
	private int hit;
	private int categoryId;
}
