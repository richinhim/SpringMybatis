package com.proto.net.mose.service;

import java.util.List;

import com.proto.net.mose.dto.moseDto;

public interface IMoseService {

	public void create () throws Exception;
	public List<moseDto> getList() throws Exception;
	public void update (moseDto dto) throws Exception;
}
