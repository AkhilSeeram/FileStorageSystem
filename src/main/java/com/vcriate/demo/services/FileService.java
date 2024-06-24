package com.vcriate.demo.services;

import com.vcriate.demo.models.File;
import com.vcriate.demo.models.FileVersion;
import com.vcriate.demo.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    File saveFile(MultipartFile file, String email) throws IOException;
    byte[] downloadFile(Long fileId);
    FileVersion saveFileVersion(Long fileId, MultipartFile file) throws IOException;
    List<FileVersion> getFileVersions(Long fileId);
    void shareFile(Long fileId, String username);
}
