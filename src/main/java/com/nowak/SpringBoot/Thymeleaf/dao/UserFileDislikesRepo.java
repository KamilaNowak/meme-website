package com.nowak.SpringBoot.Thymeleaf.dao;

import com.nowak.SpringBoot.Thymeleaf.entities.UserFileDislikes;
import com.nowak.SpringBoot.Thymeleaf.entities.UserFileLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFileDislikesRepo extends JpaRepository<UserFileDislikes,Integer> {

    UserFileDislikes findByFileId(int id);

    void delete(UserFileDislikes userFileDislike);

    List<UserFileDislikes> findAllByFileId(int fileId);

    @Query("select u from UserFileDislikes u where u.fileId=?1 and u.userId=?2")
    UserFileDislikes findByFileIdAndUserId(int fileId, int userId);
}
