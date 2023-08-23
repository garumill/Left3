package com.moyeo.mapper;

import java.util.List;

import com.moyeo.dto.Userinfo;

public interface UserinfoMapper {
	/*회원가입*/
	int insertUserinfo(Userinfo userinfo);//회원정보삽입(회원가입)
	int idCheck(String id);//아이디 중복검사
	
	/*로그인*/
	Userinfo userinfoLogin(Userinfo userinfo);//로그인 
	
	int updateLogdate(String id);//로그인 시간 변경
	int updateUserToRest(String id);//휴면계정전환
	
	/*마이페이지*/
	int updateUserinfo(Userinfo userinfo);//회원정보 변경
	int outUser(String id);//회원 탈퇴
	
	/*관리자*/
	Userinfo selectUserinfo(String id);//조건에 따른 유저 정보 검색
	List<Userinfo> selectUserinfoList();//전체 유저 정보 검색
	Userinfo selectUserinfobyRegdate(String regdate);//가입일에 따른 유저 정보 검색
}
