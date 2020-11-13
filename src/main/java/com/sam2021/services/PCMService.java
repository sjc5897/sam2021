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
            return submissionRepo.getAllByCstate("SUBMITTED");
        }
        catch(Exception ex){
            System.out.println(ex.getCause());
            return null;
        }
    }

    public SubmissionEntity getSubmissionById(Long id){
        try{
            return submissionRepo.findById(id).get(0);
        }
        catch(Exception ex){
            return null;
        }
    }

    public boolean addNewReview(Long paper_id, Long pcm_id){
        try{
            reviewRepo.save(new ReviewEntity(pcm_id,paper_id,0,"","REQUESTED"));
            return true;
        }catch (Exception ex){
            return false;
        }
    }
     public List<ReviewEntity> getReviewIdandState(Long id, String State){
         try{
             return reviewRepo.getAllByReviewerIdAndCstate(id,State);
         }
         catch(Exception ex){
             return null;
         }
     }

    public ReviewEntity getReviewById(Long id){
        try{
            return reviewRepo.findById(id).get(0);
        }catch (Exception ex){
            return null;
        }
    }

     public boolean isRequestedAlready(Long paper_id, Long uid){
        try{
            List<ReviewEntity> res = reviewRepo.findAllByReviewerIdAndPaperId(uid, paper_id);
            if(res.size() != 0){
                return false;
            }
            return true;
        }catch (Exception ex){
            return true;
        }
     }

     public boolean update(ReviewEntity reviewEntity){
        try{
            reviewRepo.save(reviewEntity);
            return true;
        }catch (Exception ex){
            return false;
        }
     }


}
