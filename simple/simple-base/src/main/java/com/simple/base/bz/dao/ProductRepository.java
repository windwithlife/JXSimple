package com.simple.base.bz.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simple.base.bz.entity.*;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
