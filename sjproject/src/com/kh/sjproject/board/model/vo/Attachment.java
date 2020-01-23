package com.kh.sjproject.board.model.vo;

import java.sql.Date;

public class Attachment {
	
	// SQL 테이블 보고 만듬
	private int fileNo;
	private int BoardId;
	private String fileOriginName;
	private String fileChangeName;
	private String filePath;
	private Date fileUploadDate;
	private int fileLevel;
	private int fileDownloadCount;
	private int fileStatus;
	
	public Attachment (){}

	public Attachment(int fileNo, int boardId, String filesOriginName, String filesChangeName, String filePath,
			Date fileUploadDate, int fileLevel, int fileDownloadCount, int fileStatus) {
		super();
		this.fileNo = fileNo;
		BoardId = boardId;
		this.fileOriginName = fileOriginName;
		this.fileChangeName = fileChangeName;
		this.filePath = filePath;
		this.fileUploadDate = fileUploadDate;
		this.fileLevel = fileLevel;
		this.fileDownloadCount = fileDownloadCount;
		this.fileStatus = fileStatus;
	}


	
	
	public Attachment(String fileOriginName, String fileChangeName, String filePath) {
		super();
		this.fileOriginName = fileOriginName;
		this.fileChangeName = fileChangeName;
		this.filePath = filePath;
	}

	public Attachment(int fileNo, int boardId, String fileOriginName, String fileChangeName, String filePath,
			Date fileUploadDate, int fileLevel, int fileDownloadCount) {
		super();
		this.fileNo = fileNo;
		BoardId = boardId;
		this.fileOriginName = fileOriginName;
		this.fileChangeName = fileChangeName;
		this.filePath = filePath;
		this.fileUploadDate = fileUploadDate;
		this.fileLevel = fileLevel;
		this.fileDownloadCount = fileDownloadCount;
	}

	public int getFileNo() {
		return fileNo;
	}

	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}

	public int getBoardId() {
		return BoardId;
	}

	public void setBoardId(int boardId) {
		BoardId = boardId;
	}

	public String getFileOriginName() {
		return fileOriginName;
	}

	public void setFileOriginName(String fileOriginName) {
		this.fileOriginName = fileOriginName;
	}

	public String getFileChangeName() {
		return fileChangeName;
	}

	public void setFileChangeName(String fileChangeName) {
		this.fileChangeName = fileChangeName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getFileUploadDate() {
		return fileUploadDate;
	}

	public void setFileUploadDate(Date fileUploadDate) {
		this.fileUploadDate = fileUploadDate;
	}

	public int getFileLevel() {
		return fileLevel;
	}

	public void setFileLevel(int fileLevel) {
		this.fileLevel = fileLevel;
	}

	public int getFileDownloadCount() {
		return fileDownloadCount;
	}

	public void setFileDownloadCount(int fileDownloadCount) {
		this.fileDownloadCount = fileDownloadCount;
	}

	public int getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(int fileStatus) {
		this.fileStatus = fileStatus;
	}

	@Override
	public String toString() {
		return "Attachment [fileNo=" + fileNo + ", BoardId=" + BoardId + ", fileOriginName=" + fileOriginName
				+ ", filesChangeName=" + fileChangeName + ", filePath=" + filePath + ", fileUploadDate="
				+ fileUploadDate + ", fileLevel=" + fileLevel + ", fileDownloadCount=" + fileDownloadCount
				+ ", fileStatus=" + fileStatus + "]";
	}
	
	
	
	
}
