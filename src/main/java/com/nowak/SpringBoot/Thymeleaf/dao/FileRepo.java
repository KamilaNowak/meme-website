package com.nowak.SpringBoot.Thymeleaf.dao;

import com.nowak.SpringBoot.Thymeleaf.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface FileRepo extends JpaRepository<File, Integer> {

    File findByUserID(int id);

    File findByTitle(String title);

    void deleteFileByTitle(String title);

    void deleteFileById(int id);

    List<File> findAllByUserID(int id);

    List<File> findAll();
}
