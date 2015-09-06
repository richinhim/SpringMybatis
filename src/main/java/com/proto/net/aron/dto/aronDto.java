package com.proto.net.aron.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class aronDto implements Serializable{


	private int id;
	
	private String name;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
