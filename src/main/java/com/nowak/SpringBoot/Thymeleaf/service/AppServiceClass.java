package com.nowak.SpringBoot.Thymeleaf.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.nowak.SpringBoot.Thymeleaf.dao.*;
import com.nowak.SpringBoot.Thymeleaf.entities.*;
import com.nowak.SpringBoot.Thymeleaf.models.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppServiceClass implements AppService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private AuthorityRepo authorityRepo;

    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ReportedRepo reportedRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ReportedCommentRepo reportedCommentRepo;

    @Autowired
    private CubbyRepo cubbyRepo;

    @Autowired
    private UserFileLikesRepo userFileLikesRepo;

    @Autowired
    private UserFileDislikesRepo userFileDislikesRepo;


    @Value("${cloud.aws.credentials.accessKey}")
    private String s3AccessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String s3SecretKey;

    @Value("${aws.bucket.name}")
    private String bucketName;

    BasicAWSCredentials credentials;
    AmazonS3 amazonS3;
    ObjectMetadata objectMetadata;

    @PostConstruct
    public void initClient() {
        credentials = new BasicAWSCredentials(s3AccessKey, s3SecretKey);

        amazonS3 = AmazonS3Client.builder()
                .withRegion("eu-central-1")
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/jpeg");
    }

    private Collection<SimpleGrantedAuthority> mapToAuthorities(Collection<Authority> authorities) {
        System.out.println("converting roles...");
        return authorities.stream().map(a -> new SimpleGrantedAuthority(a.getAuthority())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepo.findByName(s);
        System.out.println(account.getMute() + "   " + new Date());
        if (account == null)
            throw new UsernameNotFoundException("Account not found");
        if (account.getMute().before(new Date())) {
            Date today = new Date();
           // Date muteDate = new Date(today.getTime() - 9999 * (1000 * 60 * 60 * 24));
        //    Date muteDate = new Date();
          //  account.setMute(muteDate);
            account.setEnabled(true);
            return new User(account.getName(), account.getPassword(), account.isEnabled(), true, true, true, mapToAuthorities(account.getAuthorities()));
        } else if (account.getMute().after(new Date())) {
            throw new UsernameNotFoundException("Account locked until: " + account.getMute());
        } else {
            throw new UsernameNotFoundException("Account not found");
        }
    }

    @Override
    @Transactional
    public Account findByName(String userName) {
        return accountRepo.findByName(userName);
    }

    @Override
    @Transactional
    public void save(Account account) {
        accountRepo.save(account);
    }

    @Override
    public List<Account> findAllAccountsByName(String name) {
        return accountRepo.findAllByName(name);
    }

    @Override
    public void deleteAccountById(int id) {
        Account account = this.findAccountById(id);
        List<Comments> accountComments = this.findAllCommentsByUserNick(account.getName());
        List<File> accountFiles = this.findAllByUserNick(account.getName());
        for (Comments c : accountComments) {
            c.setPhoto("findAllCommentsByUserNick");
            c.setUserNick("Account Blocked");
        }
        for (File f : accountFiles) {
            f.setUserNick("Account Blocked");
        }
        accountRepo.deleteById(id);
    }

    @Override
    public Account findAccountById(int id) {
        Optional<Account> accountOptional = accountRepo.findById(id);
        Account account = null;
        if (accountOptional.isPresent()) {
            account = accountOptional.get();
        }
        return account;
    }

    @Override
    public Account convertToAccount(AccountModel accountModel) {
        Account account = new Account();
        account.setName(accountModel.getName());
        account.setEmail(accountModel.getEmail());
        account.setAuthorities(Arrays.asList(authorityRepo.findByAuthorityName("ROLE_USER")));
        return account;
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepo.findByEmail(email);
    }

    @Override
    public Account findByConfirmToken(String confirmToken) {
        return accountRepo.findByConfirmToken(confirmToken);
    }

    @Override
    public Account getLoggedAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = findByName(authentication.getName());
        return account;
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepo.findAll();
    }


    @Override
    @Transactional
    public File findByTitle(String title) {
        return fileRepo.findByTitle(title);
    }

    @Override
    public void saveFile(File file) {
        fileRepo.save(file);
    }

    @Override
    public void deleteFileByTitle(String title) {
        File file = fileRepo.findByTitle(title);
        Reported reported = this.findReportedByFileID(file.getId());
        fileRepo.delete(file);
        reportedRepo.delete(reported);
        String filePath = file.getPath();
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, filePath));
        } catch (AmazonServiceException a) {
            a.printStackTrace();
        } catch (SdkClientException s) {
            s.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void deleteFileById(int id) {
        cubbyRepo.deleteAllByFileID(id);
        reportedRepo.deleteAllByFileID(id);
        fileRepo.delete(this.findById(id));
    }

    @Override
    public void deleteFileByReportedId(int id) {
        Reported reported = this.findReportedById(id);
        int fileID = reported.getFileID();
        File file = this.findById(fileID);
        List<Cubby> cubbiesWithFile = this.findAllByFileId(fileID);
        for (Cubby c : cubbiesWithFile) {
            this.deleteCubby(c);
        }
        String filePath = file.getPath();
        fileRepo.delete(file);
        reportedRepo.delete(reported);
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, filePath));
        } catch (AmazonServiceException a) {
            a.printStackTrace();
        } catch (SdkClientException s) {
            s.printStackTrace();
        }
    }

    @Override
    public List<File> findAll() {
        return fileRepo.findAll();
    }

    @Override
    public File findById(int id) {
        Optional<File> f = fileRepo.findById(id);
        File file = null;
        if (f.isPresent()) {
            file = f.get();
        }
        return file;
    }

    @Override
    public void save(Comments comment) {
        comment.setDate(new Date());
        commentRepo.save(comment);
    }

    @Override
    public void deleteCommentAndReportedComment(int id) {
        ReportedComments rc = reportedCommentRepo.findById(id);
        int commentId = rc.getCommentID();
        System.out.println(commentId);
        reportedCommentRepo.deleteById(id);
        commentRepo.deleteById(commentId);
    }

    @Override
    public List<Comments> findAllByFileID(int id) {
        return commentRepo.findAllByFileID(id);
    }

    @Override
    public List<Comments> findAllComments() {
        return commentRepo.findAll();
    }

    @Override
    public List<Comments> commentsFromFile(List<File> files) {
        Map<File, Comments> map = new HashMap<File, Comments>();
        return null;
    }

    @Override
    public List<Comments> findAllCommentsByUserNick(String userNick) {
        return commentRepo.findAllByUserNick(userNick);
    }

    @Override
    public void deleteCommentById(int id) {
        commentRepo.deleteById(id);
    }

    @Override
    public void save(Reported reported) {
        reportedRepo.save(reported);
    }

    @Override
    public List<Reported> findAllReported() {
        return reportedRepo.findAll();
    }

    @Override
    public Reported findReportedByFileID(int id) {
        return reportedRepo.findByFileID(id);
    }

    @Override
    public Reported findReportedById(int id) {
        Optional<Reported> reported = reportedRepo.findById(id);
        Reported r = null;
        if (reported.isPresent()) {
            r = reported.get();
        }
        return r;
    }

    @Override
    public void deleteReportedById(int id) {
        reportedRepo.deleteById(id);
    }

    @Override
    public List<File> findAllByUserNick(String nick) {
        List<File> files = fileRepo.findAllByUserNick(nick);
        return files;
    }

    @Override
    public void saveReportedComment(ReportedComments reportedComment) {
        reportedCommentRepo.save(reportedComment);
    }

    @Override
    public void deleteReportedCommentById(int id) {
        reportedCommentRepo.deleteById(id);
    }

    @Override
    public void deleteCommentsFromFileById(int fileId) {
        List<Comments> commentsToDelete = commentRepo.findAllByFileID(fileId);
        for (Comments c : commentsToDelete) {
            commentRepo.delete(c);
        }
    }

    @Override
    @Transactional
    public Comments findCommentById(int id) {
        Optional<Comments> comment = commentRepo.findById(id);
        Comments cmt = null;
        if (comment.isPresent()) {
            cmt = comment.get();
        }
        return cmt;
    }

    @Override
    @Transactional
    public List<ReportedComments> findAllReportedComments() {
        return reportedCommentRepo.findAll();
    }

    @Override
    public ReportedComments findReportedCommentById(int id) {
        return reportedCommentRepo.findById(id);
    }

    @Override
    public void saveCubby(Cubby cubby) {
        cubbyRepo.save(cubby);
    }

    @Override
    public List<Cubby> findAllByEmail(String email) {
        return cubbyRepo.findAllByEmail(email);
    }

    @Override
    public List<Cubby> findAllByFileId(int id) {
        return cubbyRepo.findAllByFileID(id);
    }

    @Override
    public Cubby findCubbyById(int id) {
        Optional<Cubby> cubby = cubbyRepo.findById(id);
        Cubby c = null;
        if (cubby.isPresent())
            c = cubby.get();
        return c;
    }

    @Override
    public void deleteCubby(Cubby cubby) {
        cubbyRepo.delete(cubby);
    }

    @Override
    public void saveUserFileLike(UserFileLikes userFileLike) {
        userFileLikesRepo.save(userFileLike);
    }

    @Override
    public void saveUserFileDislike(UserFileDislikes userFileDislike) {
        userFileDislikesRepo.save(userFileDislike);
    }

    @Override
    public UserFileDislikes findUserFileDislikeByFileId(int id) {
        return userFileDislikesRepo.findByFileId(id);
    }

    @Override
    public void deleteUserFileDislike(UserFileDislikes userFileDislike) {
        userFileDislikesRepo.delete(userFileDislike);
    }

    @Override
    public UserFileLikes findUserFileLikeByFileId(int id) {
        return userFileLikesRepo.findByFileId(id);
    }

    @Override
    public void deleteUserFileLike(UserFileLikes userFileLike) {
        userFileLikesRepo.delete(userFileLike);
    }

    @Override
    public List<UserFileLikes> findAllLikes() {
        return userFileLikesRepo.findAll();
    }

    @Override
    public List<UserFileLikes> findAllUserFileLikesByFileId(int id) {
        return userFileLikesRepo.findAllByFileId(id);
    }

    @Override
    public List<UserFileDislikes> findAllUserFileDislikesByFileId(int fileId) {
        return userFileDislikesRepo.findAllByFileId(fileId);
    }

    @Override
    public UserFileLikes findUserFileLikesByFileIdAndUserId(int fileId, int userId) {
        return userFileLikesRepo.findByFileIdAndUserId(fileId,userId);
    }

    @Override
    public UserFileDislikes findUserFileDislikesByFileIdAndUserId(int fileId, int userId) {
        return userFileDislikesRepo.findByFileIdAndUserId(fileId,userId);
    }


}
