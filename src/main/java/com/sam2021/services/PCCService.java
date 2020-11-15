package com.sam2021.services;

import com.sam2021.database.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PCCService {
    @Autowired
    SubmissionRepo submissionRepo;
    @Autowired
    ReviewRepo reviewRepo;
    @Autowired
    UserRepository userRepository;

    public void assignReview(ReviewEntity reviewEntity, SubmissionEntity submissionEntity){
        // Update Review State
        reviewEntity.setCstate("ASSIGNED");
        reviewRepo.save(reviewEntity);

        // Check if submission state upgrade
        List<ReviewEntity> reviewEntities1 = reviewRepo.getAllByPaperIdAndCstate(submissionEntity.getId(),"ASSIGNED");
        List<ReviewEntity> reviewEntities2 = reviewRepo.getAllByPaperIdAndCstate(submissionEntity.getId(),"SUBMITTED");

        if(reviewEntities1.size() + reviewEntities2.size() == 3){
            submissionEntity.setCstate("REVIEWING");
            submissionRepo.save(submissionEntity);
            List<ReviewEntity> reviewEntitiesToDelete = reviewRepo.getAllByPaperIdAndCstate(submissionEntity.getId(),"REQUESTED");
            reviewRepo.deleteAll(reviewEntitiesToDelete);
        }
    }


    public ReviewEntity getReviewByReviewId(Long id){
        try{
            return reviewRepo.findById(id).get(0);

        }catch(Exception ex){
            return null;
        }
    }

    public List<ReviewEntity> getReviewsByPaperId(Long id){
        try{
            return reviewRepo.getAllByPaperId(id);

        }catch(Exception ex){
            return null;
        }
    }

    public List<ReviewEntity> getReviewByPaperIdAndState(Long id, String State){
        try{
            return reviewRepo.getAllByPaperIdAndCstate(id,State);
        }catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
            return null;
        }
    }

    public List<ReviewEntity> getReviewByPaperId(Long id){
        try{
            return reviewRepo.getAllByPaperId(id);
        }catch (Exception ex){
            return null;
        }
    }

    public UserEntity getReviewer(Long id){
        try{
            return userRepository.findAllById(id).get(0);
        }catch (Exception ex){
            return null;
        }
    }

    public void rereview(List<ReviewEntity> reviews){
         for(ReviewEntity e : reviews){
             e.setCstate("REREVIEW");
         }
         reviewRepo.saveAll(reviews);
         SubmissionEntity submissionEntity = submissionRepo.findOneById(reviews.get(0).getPaper_id());
         submissionEntity.setCstate("REVIEWING");
         submissionRepo.save(submissionEntity);
    }

    public void submitReport(long pcc, long paper, int rating, String cmt){
        //Create the PCC Review
        ReviewEntity pccReview = new ReviewEntity(pcc, paper, rating, cmt, "PCC");
        reviewRepo.save(pccReview);

        // Change state of existing reviews
        List<ReviewEntity> reviews = reviewRepo.getAllByPaperId(paper);
        for(ReviewEntity x : reviews){
            x.setCstate("RELEASED");
        }
        reviewRepo.saveAll(reviews);

        //change state of paper
        SubmissionEntity submissionEntity = submissionRepo.findOneById(paper);
        submissionEntity.setCstate("RELEASED");
        submissionRepo.save(submissionEntity);
    }
}
