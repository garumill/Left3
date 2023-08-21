package com.moyeo.service;


import org.springframework.stereotype.Service;

import com.moyeo.dao.UserinfoDAO;
import com.moyeo.dto.Userinfo;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserinfoServiceImpl implements UserinfoService {
	private final UserinfoDAO userinfoDAO;
	
	/*회원가입*/
	
	//회원가입
	@Override
	public void registerUser(Userinfo userinfo) {
		userinfoDAO.insertUserinfo(userinfo);
	}
	
	
	/*마이페이지*/
	
	//회원정보 변경
	@Override
	public void modifyUserinfo(Userinfo userinfo) {
		userinfoDAO.updateUserinfo(userinfo);
	}
	
	//회원탈퇴
	@Override
	public void removeUserinfo(String userid) {
		userinfoDAO.outUser(userid);
	}
}
