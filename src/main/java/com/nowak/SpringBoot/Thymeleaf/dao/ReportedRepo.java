package com.nowak.SpringBoot.Thymeleaf.dao;

import com.nowak.SpringBoot.Thymeleaf.entities.Reported;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportedRepo extends JpaRepository<Reported,Integer> {

    Reported findByFileID(int id);

    List<Reported> findAllByFileID(int id);

    void deleteAllByFileID(int id);
}
