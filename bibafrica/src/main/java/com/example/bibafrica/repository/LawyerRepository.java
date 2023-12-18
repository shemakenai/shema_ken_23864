package com.example.bibafrica.repository;

import com.example.bibafrica.model.Lawyer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LawyerRepository extends JpaRepository<Lawyer, Long> {
     Optional<Lawyer> findSoldierByIdIs(Long id);
}
