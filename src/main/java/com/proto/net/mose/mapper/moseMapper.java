package com.proto.net.mose.mapper;

import java.util.List;

import javax.annotation.Resource;

import com.proto.net.mose.dto.moseDto;

@Resource(name = "dsMoseScanner")
public interface moseMapper {

	public List<moseDto> getListMose();

	public int insertMose(moseDto dto);

	public int updateMose(moseDto dto);
}
