package com.proto.net.mose.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.proto.net.mose.service.IMoseService;

@Controller
public class MoseController {

	private static Logger logger = null;
	
	@Autowired
	//@Qualifier("moseService")
	IMoseService moseService;

	
	@PostConstruct
	public void init() throws Exception {
		
		if(null == logger) {
			logger = LoggerFactory.getLogger(this.getClass());
		}
		
		logger.debug("mose Controller");
		
		moseService.create();

		try {
			moseService.update(moseService.getList().get(moseService.getList().size() - 1));
		} catch (Exception e) {

		}

	}
	
	@PreDestroy
	public void destroy() throws Exception {
		if(null != logger) {
			logger = null;
		}
	}
}
