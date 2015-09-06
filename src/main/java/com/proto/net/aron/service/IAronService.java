package com.proto.net.aron.service;

import java.util.List;

import com.proto.net.aron.dto.aronDto;

public interface IAronService {

	public void create() throws Exception;

	public List<aronDto> getList() throws Exception;

	public void update(aronDto dto) throws Exception;
}
