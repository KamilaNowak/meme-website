package com.nowak.SpringBoot.Thymeleaf.dao;

import com.nowak.SpringBoot.Thymeleaf.entities.UserFileDislikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFileDislikesRepo extends JpaRepository<UserFileDislikes,Integer> {

    UserFileDislikes findByFileId(int id);

    void delete(UserFileDislikes userFileDislike);
}
