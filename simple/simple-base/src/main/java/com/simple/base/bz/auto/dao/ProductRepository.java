package com.simple.base.bz.auto.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simple.base.bz.auto.entity.*;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
