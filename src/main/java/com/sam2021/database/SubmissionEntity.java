package com.sam2021.database;

import org.springframework.stereotype.Controller;

import javax.persistence.*;

@Entity
@Table(name="submission")
public class SubmissionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="author_contact")
    private String email;

    @Column(name="title")
    private String title;

    @Column(name="format")
    private String format;

    @Column(name="version")
    private String version;

    public SubmissionEntity(String email, String title, String format, String version){
        this.email = email;
        this.title = title;
        this.format = format;
        this.version = version;
    }

    public SubmissionEntity(){

    }
    public String getTitle(){
        return this.title;
    }
    public String getEmail(){
        return this.email;
    }
    public String getFormat(){
        return this.format;
    }
    public String getVersion(){
        return this.version;
    }

    public Long getId(){
        return this.id;
    }
}
