package com.simple.base.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simple.base.api.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

}
