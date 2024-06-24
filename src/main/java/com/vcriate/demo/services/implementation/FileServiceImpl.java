package com.vcriate.demo.services.implementation;

import com.vcriate.demo.exceptions.EntityNotFoundException;
import com.vcriate.demo.models.File;
import com.vcriate.demo.models.FileVersion;
import com.vcriate.demo.models.User;
import com.vcriate.demo.repositories.FileRepository;
import com.vcriate.demo.repositories.FileVersionRepository;
import com.vcriate.demo.repositories.UserRepository;
import com.vcriate.demo.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileVersionRepository fileVersionRepository;
    private final UserRepository userRepository;

    public FileServiceImpl(FileRepository fileRepository,FileVersionRepository fileVersionRepository, UserRepository userRepository){
        this.fileRepository=fileRepository;
        this.fileVersionRepository=fileVersionRepository;
        this.userRepository=userRepository;
    }

    @Override
    public File saveFile(MultipartFile file, String email) throws IOException {
        Optional<User> user=userRepository.findByEmail(email);
        File fileEntity = new File();
        fileEntity.setFilename(file.getOriginalFilename());
        fileEntity.setData(file.getBytes());
        fileEntity.setOwner(user.get());
        return fileRepository.save(fileEntity);
    }

    @Override
    public byte[] downloadFile(Long fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> new EntityNotFoundException("File not found"));
        return file.getData();
    }

    @Override
    public FileVersion saveFileVersion(Long fileId, MultipartFile file) throws IOException {
        File originalFile = fileRepository.findById(fileId).orElseThrow(() -> new EntityNotFoundException("File not found"));
        List<FileVersion> versions = fileVersionRepository.findByOriginalFileOrderByVersionNumberDesc(originalFile);
        int versionNumber = versions.isEmpty() ? 1 : versions.get(0).getVersionNumber() + 1;
        FileVersion fileVersion = new FileVersion();
        fileVersion.setOriginalFile(originalFile);
        fileVersion.setVersionNumber(versionNumber);
        fileVersion.setData(file.getBytes());
        fileVersion.setUploadedAt(LocalDateTime.now());
        return fileVersionRepository.save(fileVersion);
    }

    @Override
    public List<FileVersion> getFileVersions(Long fileId) {
        File originalFile = fileRepository.findById(fileId).orElseThrow(() -> new EntityNotFoundException("File not found"));
        return fileVersionRepository.findByOriginalFileOrderByVersionNumberDesc(originalFile);
    }

    @Override
    public void shareFile(Long fileId, String username) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> new EntityNotFoundException("File not found"));
        User user = userRepository.findByEmail(username).orElseThrow(()-> new EntityNotFoundException("User not found"));
        file.getSharedWithUsers().add(user);
        user.getSharedFiles().add(file);
        fileRepository.save(file);
        userRepository.save(user);
    }
}
