package com.moyeo.service;

import com.moyeo.dto.Userinfo;
import com.moyeo.exception.ExistsUserinfoException;
import com.moyeo.exception.UserinfoNotFoundException;

public interface UserinfoService {
	//회원가입
	void registerUser(Userinfo userinfo) throws ExistsUserinfoException;//회원정보삽입(회원가입)
	
	//마이페이지
	void modifyUserinfo(Userinfo userinfo) throws UserinfoNotFoundException;//회원정보변경
	void removeUserinfo(String userid) throws UserinfoNotFoundException;//회원탈퇴
}
