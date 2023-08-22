package com.moyeo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	// 회원가입 페이지 이동
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String joinGET() {
		return "userinfo/join";
	}

	// 회원가입
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinPOST(Userinfo userinfo) throws Exception {

		logger.info("join 진입");

		// 회원가입 서비스 실행
		userinfoservice.registerUser(userinfo);

		logger.info("join Service 성공");

		return "redirect:/main";

	}

	// 아이디 중복검사
	@RequestMapping(value = "/memberIdChk", method = RequestMethod.POST)
	@ResponseBody
	public String memberIdChkPOST(String id) throws Exception {

		// logger.info("memberIdChk() 진입");
		int result = userinfoservice.idCheck(id);

		// logger.info("결과값 = " + result);

		if (result != 0) {
			return "fail"; // 중복 아이디가 존재
		} else {
			return "success"; // 중복 아이디 x
		}
	} // memberIdChkPOST() 종료

	
	
	
	
	// 이메일 인증
	@GetMapping("/mailCheck")
	@ResponseBody
	public String mailCheck(String email) throws Exception {
		System.out.println("이메일 인증 요청이 들어옴!");
		System.out.println("이메일 인증 이메일 : " + email);
		return mailService.joinEmail(email);
	}

	/* 로그인 */

	// 로그인 페이지 이동
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGET() {
		return "userinfo/login";
	}

	// 로그인
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute Userinfo userinfo, HttpSession session) throws Exception {
		Userinfo authUserinfo = userinfoservice.userLogin(userinfo);
		session.setAttribute("loginUserinfo", authUserinfo);
		return "userinfo/main";
	}
}
