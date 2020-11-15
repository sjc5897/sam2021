package com.sam2021.services;


import com.sam2021.database.ReviewEntity;
import com.sam2021.database.ReviewRepo;
import com.sam2021.database.SubmissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles services for the Reviews
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>
 * Created: 10/31/20 Happy Halloween
 * Last Edit: 11/15/20
 */
@Service
public class ReviewService {
    @Autowired
    private ReviewRepo reviewRepo;

    /**
     * Gets review by ID
     * @param id    review id
     * @return      Review Entity
     */
    public ReviewEntity getReviewByReviewId(Long id){
        try{
            return reviewRepo.getOneById(id);

        }catch(Exception ex){
            return null;
        }
    }

    /**
     * Gets reviews by paper id
     * @param id    paper id
     * @return      reviews
     */
    public List<ReviewEntity> getReviewsByPaperId(Long id){
        try{
            return reviewRepo.getAllByPaperId(id);

        }catch(Exception ex){
            return null;
        }
    }

    /**
     * Adds a new review request
     * @param paper_id  paper id
     * @param pcm_id    reviewer id
     * @return          Success
     */
    public boolean addNewReview(Long paper_id, Long pcm_id){
        try{
            //check if review exists
            List<ReviewEntity> res = reviewRepo.findAllByReviewerIdAndPaperId(pcm_id, paper_id);
            if(res.size() != 0){
                return false;
            }


            reviewRepo.save(new ReviewEntity(pcm_id,paper_id,0,"","REQUESTED"));
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    /**
     * Gets review by id and state
     * @param id        reviewer id
     * @param State     state
     * @return          list of reviews
     */
    public List<ReviewEntity> getReviewerIdandState(Long id, String State){
        try{
            return reviewRepo.getAllByReviewerIdAndCstate(id,State);
        }
        catch(Exception ex){
            return null;
        }
    }

    /**
     * Updates a review
     * @param reviewEntity review to be done
     * @return If the submission should be updated
     */
    public boolean update(ReviewEntity reviewEntity){
        try{
            reviewRepo.save(reviewEntity);
            // if submit we check the submission state
            if(reviewEntity.getCstate().equals("SUBMITTED")){
                List<ReviewEntity> reviewEntities = reviewRepo.getAllByPaperIdAndCstate(reviewEntity.getPaper_id(),"SUBMITTED");
                if(reviewEntities.size()==3){
                   return true;
                }
            }
            return false;
        }catch (Exception ex){
            return false;
        }
    }


    /**
     * Gets reviews by paper id and state
     * @param id        paper id
     * @param State     review state
     * @return          reviews
     */
    public List<ReviewEntity> getReviewByPaperIdAndState(Long id, String State){
        try{
            return reviewRepo.getAllByPaperIdAndCstate(id,State);
        }catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Sets state of reviews to re-review
     * @param reviews reviews to be updated
     */
    public boolean rereview(List<ReviewEntity> reviews){
        for(ReviewEntity e : reviews){
            e.setCstate("REREVIEW");
        }
        reviewRepo.saveAll(reviews);
        return true;
    }

    /**
     * Submits the a report review
     * @param pcc       pcc id
     * @param paper     paper id
     * @param rating    pcc rating
     * @param cmt       pcc cmt
     */
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
    }

    /**
     * Assigns review
     * @param reviewEntity
     */
    public boolean assignReview(ReviewEntity reviewEntity) {

        try{
            // Update Review State
            reviewEntity.setCstate("ASSIGNED");
            reviewRepo.save(reviewEntity);

            List<ReviewEntity> reviews1 = getReviewByPaperIdAndState(reviewEntity.getPaper_id(), "ASSIGNED");
            List<ReviewEntity> reviews2 = getReviewByPaperIdAndState(reviewEntity.getPaper_id(), "SUBMITTED");
            if(reviews1.size() + reviews2.size() == 3){
                return true;
            }
            return false;
        }
        catch (Exception ex){
            return false;
        }



    }


}
