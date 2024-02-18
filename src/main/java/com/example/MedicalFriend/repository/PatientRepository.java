package com.example.MedicalFriend.repository;

import com.example.MedicalFriend.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {


    @Query
    Optional<PatientEntity> findByEmail(String email);

}
