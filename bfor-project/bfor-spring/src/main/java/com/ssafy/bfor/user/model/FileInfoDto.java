package com.ssafy.bfor.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileInfoDto {
	private String userId;
	private String saveFolder;
	private String originalFile;
	private String saveFile;

//	public String getSaveFolder() {
//		return saveFolder;
//	}
//
//	public void setSaveFolder(String saveFolder) {
//		this.saveFolder = saveFolder;
//	}
//
//	public String getOriginalFile() {
//		return originalFile;
//	}
//
//	public void setOriginalFile(String originalFile) {
//		this.originalFile = originalFile;
//	}
//
//	public String getSaveFile() {
//		return saveFile;
//	}
//
//	public void setSaveFile(String saveFile) {
//		this.saveFile = saveFile;
//	}

}
