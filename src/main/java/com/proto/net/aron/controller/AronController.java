package com.proto.net.aron.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.proto.net.aron.service.IAronService;

@Controller
public class AronController {

	private static Logger logger = null;
	
	@Autowired
	@Qualifier("aronService")
	IAronService aronService;

	@PostConstruct
	public void init() throws Exception {
		
		if(null == logger) {
			logger = LoggerFactory.getLogger(this.getClass());
		}
		
		logger.debug("aron Controller");
		aronService.create();

		try {
			aronService.update(aronService.getList().get(aronService.getList().size() - 1));
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
