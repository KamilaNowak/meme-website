package com.nowak.SpringBoot.Thymeleaf.dao;

import com.nowak.SpringBoot.Thymeleaf.entities.UserFileLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFileLikesRepo extends JpaRepository<UserFileLikes,Integer> {

    UserFileLikes findByFileId(int id);

    void delete(UserFileLikes userFileLike);
}
