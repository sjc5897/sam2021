package com.sam2021.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<ReviewEntity,String>{
    ReviewEntity getOneById(Long id);

    ReviewEntity getOneByReviewerIdAndPaperId(Long reviewer_id, Long paper_id);

    List<ReviewEntity> getAllByReviewerIdAndCstate(Long id, String State);
    List<ReviewEntity> findAllByReviewerIdAndPaperId(Long review, Long paper);
    List<ReviewEntity> findById(Long id);
    List<ReviewEntity> getAllByPaperIdAndCstate(Long id, String State);
    List<ReviewEntity> getAllByPaperId(Long id);
}
