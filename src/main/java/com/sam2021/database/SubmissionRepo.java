package com.sam2021.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepo extends JpaRepository<SubmissionEntity,String> {
    List<SubmissionEntity> findAll();
    List<SubmissionEntity> findAllByEmail(String email);
    List<SubmissionEntity> findById(Long Id);
    List<SubmissionEntity> findByTitle(String title);
    List<SubmissionEntity> findAllByC_state(SubmissionEntity.State state);
}
