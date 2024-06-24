package com.vcriate.demo.controllers;

import com.vcriate.demo.models.FileVersion;
import com.vcriate.demo.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {
    private FileService fileService;

    public FileController(FileService fileService){
        this.fileService=fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file) throws IOException {
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            fileService.saveFile(file, email);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed");
        }

    }
    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        byte[] data = fileService.downloadFile(fileId);
        return ResponseEntity.ok(data);
    }
    @PostMapping("/version/{fileId}")
    public ResponseEntity<String> uploadFileVersion(@PathVariable Long fileId, @RequestParam("file") MultipartFile file){
        try {
            fileService.saveFileVersion(fileId, file);
            return ResponseEntity.ok("File version uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File version upload failed");
        }
    }
    @GetMapping("/versions/{fileId}")
    public ResponseEntity<List<FileVersion>> getFileVersions(@PathVariable Long fileId) {
        List<FileVersion> versions = fileService.getFileVersions(fileId);
        return ResponseEntity.ok(versions);
    }
    @PostMapping("/share/{fileId}")
    public ResponseEntity<String> shareFile(@PathVariable Long fileId, @RequestParam String username){
        fileService.shareFile(fileId, username);
        return ResponseEntity.ok("File shared successfully");
    }
}
