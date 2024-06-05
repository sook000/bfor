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
public class UserDto {

	private String userId;
	private String userPassword;
	private String userName;
	private String userTel;
	private String userBirth;
	private String userEmailId;
	private String userEmailDomain;
	private String regdate;
//	private String profileImage;
	private FileInfoDto profileImage;
	private int sidoCode;
	private int gugunCode;
	private String token;
}
