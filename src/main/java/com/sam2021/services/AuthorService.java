package com.sam2021.services;

import com.sam2021.database.SubmissionEntity;
import com.sam2021.database.SubmissionRepo;
import com.sam2021.database.UserEntity;
import com.sam2021.database.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    SubmissionRepo submissionRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServletContext context;

    public UserEntity getAuthor(String email){
        try{
            return userRepository.findByEmail(email).get(0);
        }
        catch (Exception ex){
            return null;
        }
    }

    public List<SubmissionEntity> getAuthorsSubmissions(String email){
        try {
            return submissionRepo.findAllByEmail(email);
        }
        catch (Exception ex){
            return null;
        }

    }
    public boolean addNewSubmission(String email, String title, String file_name, String format,String author_list,int version, int author_id){
        try {
            submissionRepo.save(new SubmissionEntity(email,title,file_name,format,author_list,version,author_id,"SUBMITTED"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void uploadFile(MultipartFile file) {
        try {
            Path copyLocation = Paths
                    .get(context.getRealPath("/") +"uploadDir"+ File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
