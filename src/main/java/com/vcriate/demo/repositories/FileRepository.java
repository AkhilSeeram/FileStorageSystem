package com.vcriate.demo.repositories;

import com.vcriate.demo.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File,Long> {
}
