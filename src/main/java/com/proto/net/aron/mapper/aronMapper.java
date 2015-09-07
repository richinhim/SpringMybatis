package com.proto.net.aron.mapper;

import java.util.List;

import javax.annotation.Resource;

import com.proto.net.aron.dto.aronDto;

//@Resource(name = "dsAronScanner")
public interface aronMapper {

	public List<aronDto> getListAron();
	
	public int insertAron(aronDto dto);
	
	public int updateAron(aronDto dto);
	
}
