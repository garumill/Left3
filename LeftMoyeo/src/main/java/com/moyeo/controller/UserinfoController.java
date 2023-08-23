package com.moyeo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	private MailSendService mailService;

	@Autowired
	private BCryptPasswordEncoder pwEncoder;

	// 회원가입 페이지 이동
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String joinGET() {
		return "userinfo/join";
	}

	// 회원가입
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinPOST(Userinfo userinfo) throws Exception {

		// 회원가입 서비스 실행
		// userinfoservice.registerUser(userinfo);

		String rawPw = ""; // 인코딩 전 비밀번호
		String encodePw = ""; // 인코딩 후 비밀번호

		rawPw = userinfo.getPw();
		encodePw = pwEncoder.encode(rawPw); // 인코딩
		userinfo.setPw(encodePw);

		userinfoservice.registerUser(userinfo);// 회원가입 쿼리 실행

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

		/*
		 * HttpSession session = request.getSession(); Userinfo lto =
		 * userinfoservice.userLogin(userinfo);
		 * 
		 * if (lto == null) { // 일치하지 않는 아이디, 비밀번호 입력 경우
		 * 
		 * int result = 0; rttr.addFlashAttribute("result", result); return
		 * "redirect:/user/login";
		 * 
		 * }
		 * 
		 * session.setAttribute("userinfo", lto); // 일치하는 아이디, 비밀번호 경우 (로그인 성공) return
		 * "redirect:/user/main";
		 */

		HttpSession session = request.getSession();
		String rawPw = "";
		String encodePw = "";

		Userinfo lto = userinfoservice.userLogin(userinfo);

		if (lto != null) {// 일치하는 아이디 존재

			rawPw = userinfo.getPw(); // 사용자가 제출한 비밀번호
			encodePw = lto.getPw(); // 데이터베이스에 저장한 인코딩된 비밀번호

			if (true == pwEncoder.matches(rawPw, encodePw)) { // 비밀번호 일치여부 판단

				lto.setPw(""); // 인코딩된 비밀번호 정보 지움
				session.setAttribute("userinfo", lto); // session에 사용자의 정보 저장

				userinfoservice.updateUserLogindate(lto.getId());// 마지막 로그인 날짜 업데이트

				return "redirect:/user/main"; // 메인페이지 이동

			} else {
				rttr.addFlashAttribute("result", 0);
				return "redirect:/user/login"; // 로그인 페이지 이동
			}

		} else { // 일치하는 아이디 미존재 (로그인 실패)
			rttr.addFlashAttribute("result", 0);
			return "redirect:/user/login"; // 로그인 페이지 이동
		}

	}

	// 로그아웃 후 로그인 페이지로 이동
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();

		return "redirect:/user/login";
	}

	/* 메인페이지 이동 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String mainGET() {
		return "userinfo/main";
	}
	
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String userGET(Model model, HttpSession session) {
		// 사용자 정보와 마지막 로그인 시간 가져오기
		Userinfo userinfo = (Userinfo) session.getAttribute("userinfo");

		if (userinfo == null) {
			// 로그인되지 않은 상태에서 main 페이지 접근 시 처리 (예: 로그인 페이지로 리디렉션)
			return "redirect:/user/login";
		}

		// 사용자 정보에서 마지막 로그인 시간 가져오기
		String lastLoginDate = userinfo.getLogdate(); // 사용자 정보의 마지막 로그인 시간 필드를 가져온다고 가정

		// 모델에 데이터 추가
		model.addAttribute("lastLoginDate", lastLoginDate);

		return "userinfo/user";
	}

	/* 마이페이지 */

	// 정보 수정 페이지로 이동
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(HttpSession session) {
		session.invalidate();

		return "userinfo/modify";
	}

}
