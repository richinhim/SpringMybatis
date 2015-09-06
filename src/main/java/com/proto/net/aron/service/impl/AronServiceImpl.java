package com.proto.net.aron.service.impl;

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

import com.proto.net.aron.dto.aronDto;
import com.proto.net.aron.mapper.aronMapper;
import com.proto.net.aron.service.IAronService;

@Service(value = "aronService")
public class AronServiceImpl implements IAronService {

	private static Logger logger = null;

	@Autowired
	aronMapper aronMappers;

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
		aronDto dto = null;
		int value = 0;

		try {
			logger.info("START====================");

			random = new Random();
			value = Math.abs(random.nextInt());
			dto = new aronDto();
			dto.setId(value);
			dto.setName("aron");

			logger.info("dto==>" + dto.getName());

			aronMappers.insertAron(dto);

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

	public List<aronDto> getList() throws Exception {
		return aronMappers.getListAron();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(aronDto dto) throws Exception {
		dto.setName(dto.getName() + "222");
		aronMappers.updateAron(dto);

		throw new Exception("error");
	}
}
