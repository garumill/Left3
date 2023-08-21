package com.moyeo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.moyeo.dto.Userinfo;
import com.moyeo.mapper.UserinfoMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserinfoDAOImpl implements UserinfoDAO {
	private final SqlSession sqlSession;
	
	//회원가입
	@Override
	public int insertUserinfo(Userinfo userinfo) {
		return sqlSession.getMapper(UserinfoMapper.class).insertUserinfo(userinfo);
	}
	
	//아이디 중복검사
	@Override
	public int idCheck(String id) {
		return sqlSession.getMapper(UserinfoMapper.class).idCheck(id);
	}
	
	
	@Override
	public Userinfo userinfoLogin(Userinfo userinfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateLogdate(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateUserToRest(String id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//마이페이지
	
	//회원정보변경
	@Override
	public int updateUserinfo(Userinfo userinfo) {
		return sqlSession.getMapper(UserinfoMapper.class).updateUserinfo(userinfo);
	}
	
	//회원탈퇴
	@Override
	public int outUser(String id) {
		return sqlSession.getMapper(UserinfoMapper.class).outUser(id);
	}

	@Override
	public Userinfo selectUserinfo(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Userinfo> selectUserinfoList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Userinfo selectUserinfobyRegdate(String regdate) {
		// TODO Auto-generated method stub
		return null;
	}



}
