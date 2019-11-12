package com.nowak.SpringBoot.Thymeleaf.controllers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.nowak.SpringBoot.Thymeleaf.entities.Account;
import com.nowak.SpringBoot.Thymeleaf.entities.File;
import com.nowak.SpringBoot.Thymeleaf.models.MemeModel;
import com.nowak.SpringBoot.Thymeleaf.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class S3Controller {

    @Autowired
    private AppService appService;

    @Value("${cloud.aws.credentials.accessKey}")
    private String s3AccessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String s3SecretKey;

    @Value("${aws.bucket.name}")
    private String bucketName;

    BasicAWSCredentials credentials;
    AmazonS3 amazonS3;

    @PostConstruct
    public void initClient() {
        credentials = new BasicAWSCredentials(s3AccessKey, s3SecretKey);

        amazonS3 = AmazonS3Client.builder()
                .withRegion("eu-central-1")
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    @PostMapping("/upload")
    public String addMeme(@RequestParam("file") MultipartFile file,
                          HttpServletRequest httpServletRequest,
                          Model model,
                          @Valid @ModelAttribute("memeModel") MemeModel memeModel,
                          BindingResult bindingResult) {
        if (file.isEmpty()) {
            model.addAttribute("upload_error", "Select file to upload");
            return "upload-meme";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("upload_error", bindingResult.getFieldError().getDefaultMessage());
            return "upload-meme";
        }

        try {
            InputStream inputStream = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpeg");

            amazonS3.putObject(new PutObjectRequest(bucketName,
                    file.getOriginalFilename(), inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, file.getOriginalFilename()));
            String path = s3Object.getObjectContent().getHttpRequest().getURI().toString();

            model.addAttribute("file", path);
            httpServletRequest.setAttribute("file", path);
            model.addAttribute("upload_success", "Uploaded successfully. \nLink: " + path);

            Account account = appService.getLoggedAccount();
            int accountID = account.getId();
            File appFile = new File();
            appFile.setUserID(accountID);
            appFile.setTitle(memeModel.getTitle());
            appFile.setPath(path);
            appService.saveFile(appFile);

        } catch (IOException e) {
            model.addAttribute("upload_error", e.getMessage());
        }
        return "upload-meme";
    }
}
