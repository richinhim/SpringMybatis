package com.proto.net.mose.service.impl;

import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.proto.net.mose.dto.moseDto;
import com.proto.net.mose.mapper.moseMapper;
import com.proto.net.mose.service.IMoseService;

@Service(value = "moseService")
public class MoseServiceImpl implements IMoseService{


	private static Logger logger = null;

	@Autowired
	moseMapper moseMappers;

	@PostConstruct
	public void init() throws Exception {
		if (null == logger) {
			logger = LoggerFactory.getLogger(this.getClass());
		}
	}

	@PreDestroy
	public void destroy() throws Exception {
		if (null != logger) {
			logger = null;
		}
	}

	
	public void create() throws Exception {
		Random random = null;
		moseDto dto = null;
		int value = 0;

		try {
			logger.info("START====================");

			random = new Random();
			value = Math.abs(random.nextInt());
			dto = new moseDto();
			dto.setId(value);
			dto.setName("mose");

			logger.info("dto==>" + dto.getName());

			moseMappers.insertMose(dto);

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (null != random) {
				random = null;
			}
			if (null != dto) {
				dto = null;
			}
			logger.info("END====================");
		}
	}

	public List<moseDto> getList() throws Exception {
		return moseMappers.getListMose();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(moseDto dto) throws Exception {
		dto.setName(dto.getName() + "222");
		moseMappers.updateMose(dto);

		throw new Exception("error");
	}
}
