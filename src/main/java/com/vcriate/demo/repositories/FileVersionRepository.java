package com.vcriate.demo.repositories;

import com.vcriate.demo.models.File;
import com.vcriate.demo.models.FileVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FileVersionRepository extends JpaRepository<FileVersion,Long> {
    List<FileVersion> findByOriginalFileOrderByVersionNumberDesc(File file);
}
//findByFileIdOrderByVersionNumberDesc