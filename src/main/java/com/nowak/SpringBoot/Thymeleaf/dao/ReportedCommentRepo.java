package com.nowak.SpringBoot.Thymeleaf.dao;

import com.nowak.SpringBoot.Thymeleaf.entities.ReportedComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportedCommentRepo extends JpaRepository<ReportedComments,Integer> {
}
