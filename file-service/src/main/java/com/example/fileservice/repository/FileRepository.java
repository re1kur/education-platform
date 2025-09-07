package com.example.fileservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.fileservice.entity.File;

import java.util.UUID;

@Repository
public interface FileRepository extends CrudRepository<File, UUID> {
}
