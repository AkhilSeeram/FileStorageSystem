package com.vcriate.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] data;

    private String filename;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "originalFile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FileVersion> versions;

    @ManyToMany(mappedBy = "sharedFiles")
    private Set<User> sharedWithUsers;

}
