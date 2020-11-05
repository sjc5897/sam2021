package com.sam2021.services;

import com.sam2021.database.SubmissionEntity;
import com.sam2021.database.SubmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {
    @Autowired
    SubmissionRepo submissionRepo;
    public List<SubmissionEntity> getSubmission(Long id){
        return submissionRepo.findById(id);
    }
}
