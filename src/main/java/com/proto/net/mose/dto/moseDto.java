package com.proto.net.mose.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class moseDto implements Serializable {

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
