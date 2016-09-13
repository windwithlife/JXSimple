package com.simple.base.bz.auto.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.simple.base.bz.auto.entity.*;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public  List<Product> findByName(String name);
    public  Product findOneByName(String name);

    
}
