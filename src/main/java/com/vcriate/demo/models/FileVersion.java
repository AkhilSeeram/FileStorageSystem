package com.vcriate.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class FileVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;

    private int versionNumber;

    @Lob
    private byte[] data;

    private LocalDateTime uploadedAt;

}
