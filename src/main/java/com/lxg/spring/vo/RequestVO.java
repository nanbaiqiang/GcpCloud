package com.lxg.spring.vo;

public class RequestVO {
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getClientNum() {
		return clientNum;
	}

	public void setClientNum(String clientNum) {
		this.clientNum = clientNum;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String searchType =  "1";
	
	public String clientNum;
	
	public String name;
	

}
