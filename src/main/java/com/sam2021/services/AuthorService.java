package com.sam2021.services;

import com.sam2021.database.SubmissionEntity;
import com.sam2021.database.SubmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    SubmissionRepo submissionRepo;

    public List<SubmissionEntity> getAuthorsSubmissions(String email){
        try {
            return submissionRepo.findAllByEmail(email);
        }
        catch (Exception ex){
            return null;
        }

    }
}
