package com.sam2021.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repo for the Submission Entity, Represents an interface for accessing the JpaRepo
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>, Malcolm Lambrecht
 * Created: 10/26/20
 * Last Edit: 11/15/20
 */
@Repository
public interface SubmissionRepo extends JpaRepository<SubmissionEntity,String> {
    /**
     * Gets all submissions
     * @return List of all submission entities
     */
    List<SubmissionEntity> findAll();

    /**
     * Gets a submission by Id
     * @param Id  id of the submission
     * @return    A submission entity
     */
    SubmissionEntity findOneById(Long Id);

    /**
     * Gets all submissions by current state
     * @param state The state we wish to query for
     * @return      List of submission entities
     */
     List<SubmissionEntity> getAllByCstate(String state);

    /**
     * Gets all submissions by the author
     * @param id    The id of the author
     * @return      List of submission entities
     */
    List<SubmissionEntity> getAllByAuthorId(Long id);
}
