package com.simple.base.components.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.base.components.user.dao.UserInfoRepository;
import com.simple.base.components.user.dao.UserRepository;
import com.simple.base.components.user.entity.User;
import com.simple.base.components.user.entity.UserInfo;

@Service
public class UserService {
	@Autowired
	private UserRepository userDao;

	@Autowired
	private UserInfoRepository userInfoDao;
	
	public User findByUsername(String username) {
	       System.out.println("UserInfoServiceImpl.findByUsername()");
	       return userDao.findByUsername(username);
   }
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
