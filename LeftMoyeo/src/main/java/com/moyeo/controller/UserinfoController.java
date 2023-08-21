package com.moyeo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moyeo.dto.Userinfo;
import com.moyeo.service.MailSendService;
import com.moyeo.service.UserinfoService;

@Controller

@RequestMapping(value = "/user")
public class UserinfoController {
	private static final Logger logger = LoggerFactory.getLogger(UserinfoController.class);

	@Autowired
	private UserinfoService userinfoservice;
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailSendService mailService;

	//회원가입 페이지 이동
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String joinGET() {
		return "userinfo/join";
	}

	//회원가입
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinPOST(Userinfo userinfo) throws Exception {

		logger.info("join 진입");

		//회원가입 서비스 실행
		userinfoservice.registerUser(userinfo);

		logger.info("join Service 성공");

		return "redirect:/main";

	}
	
	
	//이메일 인증
	@RequestMapping(value = "/mailCheck", method = RequestMethod.GET)
	@ResponseBody
	public void mailCheck(String email) throws Exception {
		System.out.println("이메일 인증 요청이 들어옴!");
		System.out.println("이메일 인증 이메일 : " + email);
	}
	
	

	//로그인 페이지 이동
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void loginGET() {
		logger.info("로그인 페이지 진입");
	}
}
