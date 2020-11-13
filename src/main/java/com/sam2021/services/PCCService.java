package com.sam2021.services;

import com.sam2021.database.ReviewRepo;
import com.sam2021.database.SubmissionEntity;
import com.sam2021.database.SubmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PCCService {
    @Autowired
    SubmissionRepo submissionRepo;
    @Autowired
    ReviewRepo reviewRepo;

    public List<SubmissionEntity> getSubmissionbyState(String state){
        try{
            return submissionRepo.getAllByCstate(state);
        }catch (Exception ex){
            return null;
        }
    }
}
