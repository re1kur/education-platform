package com.example.balanceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re1kur.transactionservice.entity.Status;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    Optional<Status> findByName(String approved);
}
