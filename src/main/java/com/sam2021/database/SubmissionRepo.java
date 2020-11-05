package com.sam2021.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepo extends JpaRepository<SubmissionEntity,String> {
    List<SubmissionEntity> findAll();
    List<SubmissionEntity> findAllByEmail(String email);
}
