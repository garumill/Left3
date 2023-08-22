package com.moyeo.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.moyeo.dao.UserinfoDAO;
import com.moyeo.dto.Userinfo;
import com.moyeo.exception.LoginAuthFailException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserinfoServiceImpl implements UserinfoService {
	private final UserinfoDAO userinfoDAO;

	/* 회원가입 */

	// 회원가입
	@Override
	public void registerUser(Userinfo userinfo) {
		userinfoDAO.insertUserinfo(userinfo);
	}

	// 아이디 검사
	@Override
	public int idCheck(String id) throws Exception {
		return userinfoDAO.idCheck(id);
	}

	/* 로그인 */
	@Override
	public Userinfo userLogin(Userinfo userinfo) throws LoginAuthFailException {
		// 매개변수로 전달받은 회원정보의 아이디로 기존 회원정보를 검색하여 검색결과를 반환받아 저장
		Userinfo authUserinfo = userinfoDAO.selectUserinfo(userinfo.getId());

		// 검색된 회원정보가 없는 경우 - 아이디 인증 실패
		if (authUserinfo == null) {
			throw new LoginAuthFailException("아이디의 회원정보가 존재하지 않습니다.", userinfo.getId());
		}
		
		
		// 매개변수로 전달받은 회원정보의 비밀번호와 검색된 회원정보의 비밀번호를 비교하여
		// 같지 않은 경우 - 비밀번호 인증 실패
		if (!BCrypt.checkpw(userinfo.getPw(), authUserinfo.getPw())) {
			throw new LoginAuthFailException("아이디가 없거나 비밀번호가 맞지 않습니다.", userinfo.getId());
		}

		// 매개변수로 전달받은 회원정보의 아이디로 검색된 회원정보 반환
		return authUserinfo;
	}

	/* 마이페이지 */

	// 회원정보 변경
	@Override
	public void modifyUserinfo(Userinfo userinfo) {
		userinfoDAO.updateUserinfo(userinfo);
	}

	// 회원탈퇴
	@Override
	public void removeUserinfo(String userid) {
		userinfoDAO.outUser(userid);
	}

}
