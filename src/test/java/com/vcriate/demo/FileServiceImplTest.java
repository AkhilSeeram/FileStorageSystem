package com.vcriate.demo;

import com.vcriate.demo.models.File;
import com.vcriate.demo.models.User;
import com.vcriate.demo.repositories.FileRepository;
import com.vcriate.demo.repositories.FileVersionRepository;
import com.vcriate.demo.repositories.UserRepository;
import com.vcriate.demo.services.implementation.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FileServiceImplTest {
    @Mock
    private FileRepository fileRepository;

    @Mock
    private FileVersionRepository fileVersionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FileServiceImpl fileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveFile() throws IOException {
        String email = "test@example.com";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        File savedFile = new File();
        savedFile.setFilename("test.txt");

        when(fileRepository.save(any(File.class))).thenReturn(savedFile);
        File result = fileService.saveFile(file, email);

        assertNotNull(result);
        assertEquals("test.txt", result.getFilename());
        verify(fileRepository, times(1)).save(any(File.class));
    }

    @Test
    public void testDownloadFile() {
        Long fileId = 1L;
        File file = new File();
        file.setId(fileId);
        file.setData("Hello, World!".getBytes());

        when(fileRepository.findById(fileId)).thenReturn(Optional.of(file));

        byte[] data = fileService.downloadFile(fileId);

        assertNotNull(data);
        assertEquals("Hello, World!", new String(data));
    }
}
