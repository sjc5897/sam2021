package com.sam2021.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    List<UserEntity> findAllById(Long id );
    List<UserEntity> findByEmail(String email);
    UserEntity findOneById(Long id);

}