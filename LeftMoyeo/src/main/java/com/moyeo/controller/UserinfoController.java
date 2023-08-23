package com.moyeo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPOST(HttpServletRequest request, Userinfo userinfo, RedirectAttributes rttr) throws Exception {

		// System.out.println("login 메서드 진입");
		// System.out.println("전달된 데이터 : " + userinfo);

		HttpSession session = request.getSession();
		Userinfo lto = userinfoservice.userLogin(userinfo);

		if (lto == null) { // 일치하지 않는 아이디, 비밀번호 입력 경우

			int result = 0;
			rttr.addFlashAttribute("result", result);
			return "redirect:/user/login";

		}

		session.setAttribute("userinfo", lto); // 일치하는 아이디, 비밀번호 경우 (로그인 성공)
		return "redirect:/user/main";

	}
	
	/* 메인페이지 이동 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String mainGET() {
		return "userinfo/main";
	}
}
