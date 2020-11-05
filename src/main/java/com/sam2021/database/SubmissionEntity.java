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

    public SubmissionEntity(String email, String title, String format){
        this.email = email;
        this.title = title;
        this.format = format;
    }

    public SubmissionEntity(){

    }

}
