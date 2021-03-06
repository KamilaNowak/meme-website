package com.nowak.SpringBoot.Thymeleaf.dao;

import com.nowak.SpringBoot.Thymeleaf.entities.UserFileLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFileLikesRepo extends JpaRepository<UserFileLikes,Integer> {

    UserFileLikes findByFileId(int id);

    void delete(UserFileLikes userFileLike);

    List<UserFileLikes> findAll();

    List<UserFileLikes> findAllByFileId(int id);

    @Query("select u from UserFileLikes u where u.fileId=?1 and u.userId=?2")
    UserFileLikes findByFileIdAndUserId(int fileId, int userId);
}
