package com.nowak.SpringBoot.Thymeleaf.dao;

import com.nowak.SpringBoot.Thymeleaf.entities.Cubby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CubbyRepo extends JpaRepository<Cubby, Integer> {

    List<Cubby> findAllByEmail(String email);

    List<Cubby> findAllByFileID(int id);

    void deleteAllByFileID(int id);
}
