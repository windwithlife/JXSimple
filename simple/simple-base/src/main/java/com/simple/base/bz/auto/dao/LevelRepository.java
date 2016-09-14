package com.simple.base.bz.auto.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.simple.base.bz.auto.entity.*;

public interface LevelRepository extends JpaRepository<Level, Long> {
    public  List<Level> findByName(String name);
    public  List<Level> findByNameLike(String name);

    public  Level findOneByName(String name);

    
}
