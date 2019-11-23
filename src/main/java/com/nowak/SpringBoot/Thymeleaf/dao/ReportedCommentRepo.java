package com.nowak.SpringBoot.Thymeleaf.dao;

import com.nowak.SpringBoot.Thymeleaf.entities.ReportedComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportedCommentRepo extends JpaRepository<ReportedComments,Integer> {

    void deleteById(int id);

    List<ReportedComments> findAll();

    ReportedComments findById(int id);
}
