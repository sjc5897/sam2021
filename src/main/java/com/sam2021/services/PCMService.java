package com.sam2021.services;

import com.sam2021.database.ReviewEntity;
import com.sam2021.database.ReviewRepo;
import com.sam2021.database.SubmissionEntity;
import com.sam2021.database.SubmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to assist users in reviews.
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>
 * Created: 11/4/20
 * Last Edit: 11/13/20
 */
@Service
public class PCMService {
    @Autowired
    SubmissionRepo submissionRepo;
    @Autowired
    ReviewRepo reviewRepo;

    public List<SubmissionEntity> getAllSubmitted(){
        try{
            return submissionRepo.findAllByState(SubmissionEntity.State.SUBMITTED);
        }
        catch(Exception ex){
            return null;
        }
    }

    public List<ReviewEntity> getAllAssignedReviews(Long id){
        try{
            return reviewRepo.findAllByReviewerIdAndState(id, ReviewEntity.State.ASSIGNED);
        }
        catch(Exception ex){
            return null;
        }
    }
    public List<ReviewEntity> getAllSubmittedReviews(Long id){
        try{
            return reviewRepo.findAllByReviewerIdAndState(id, ReviewEntity.State.SUBMITTED);
        }
        catch(Exception ex){
            return null;
        }
    }


}
