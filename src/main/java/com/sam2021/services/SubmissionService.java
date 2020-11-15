package com.sam2021.services;

import com.sam2021.database.SubmissionEntity;
import com.sam2021.database.SubmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for accessing the data of the submission repo
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>, Malcolm Lambrecht
 * Created: 10/26/20
 * Last Edit: 11/15/20
 */
@Service
public class SubmissionService {
    // Wire Repo
    @Autowired
    SubmissionRepo submissionRepo;

    /**
     * Gets a submission by an id
     * @param id the id of the submission
     * @return  a Submission Entity
     */
    public SubmissionEntity getSubmission(Long id){
        return submissionRepo.findOneById(id);
    }

    /**
     * Gets all the submissions by author id
     * @param id    author id
     * @return      List of return submissions
     */
    public List<SubmissionEntity> getAuthorsSubmissions(Long id){
        try{
            return submissionRepo.getAllByAuthorId(id);
        }catch (Exception ex){
            return null;
        }
    }

    /**
     * Adds new submission to the database
     * @param email         String representing contact email address
     * @param title         String representing title
     * @param file_name     String File representing file path
     * @param format        String representing the file format
     * @param author_list   String representing the authors
     * @param version       String representing the version of the paper
     * @param author_id     Long representing the author
     * @return              Boolean representing success
     */
    public boolean addNewSubmission(String email, String title, String file_name, String format,String author_list,int version, Long author_id){
        try {
            submissionRepo.save(new SubmissionEntity(email,title,file_name,format,author_list,version,author_id,"SUBMITTED"));
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
