package com.simple.base.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simple.base.api.entity.UserClientInfo;

public interface UserClientInfoRepository extends JpaRepository<UserClientInfo, Long> {

}
