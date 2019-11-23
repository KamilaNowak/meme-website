package com.nowak.SpringBoot.Thymeleaf.dao;

import com.nowak.SpringBoot.Thymeleaf.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comments,Integer> {

    List<Comments> findAllByFileID(int id);

    List<Comments> findAllByUserNick(String userNick);

    void deleteById(int id);
}
