package com.simple.base.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.base.api.dao.UserInfoRepository;
import com.simple.base.api.dao.UserRepository;
import com.simple.base.api.entity.User;
import com.simple.base.api.entity.UserInfo;

@Service
public class UserService {
	@Autowired
	private UserRepository userDao;

	@Autowired
	private UserInfoRepository userInfoDao;
	public long register(User user) {

		User savedUser = userDao.saveAndFlush(user);
		return savedUser.getId();
	}
	
	public int login(User user){
		User matchUser = userDao.findByIdAndPassword(user.getId(),user.getPassword());
		if (null != matchUser){
			Integer status = 1;
			matchUser.setStatus(status.byteValue());
			userDao.save(matchUser);
			return 0;
		}else{
			return -1;
		}
	}
	
	public int logout(User user){
		User matchUser = userDao.findById(user.getId());
		if (null != matchUser){
			Integer status = 0;
			matchUser.setStatus(status.byteValue());
			userDao.save(matchUser);
			return 0;
		}else{
			return -1;
		}
	}
	
	public void updateUserInfo(UserInfo info){
		userInfoDao.saveAndFlush(info);
	}
}
