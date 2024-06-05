package com.ssafy.bfor.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GugunDto {
	private int sidoCode;
	private int gugunCode;
	private String gugunName;
}
