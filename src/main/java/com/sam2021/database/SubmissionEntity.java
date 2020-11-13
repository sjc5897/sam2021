package com.sam2021.database;

import org.springframework.stereotype.Controller;

import javax.persistence.*;

@Entity
@Table(name="submission")
public class SubmissionEntity {
    public enum State{
        SUBMITTED,
        ASSIGNED,
        REVIEWED,
        RELEASED
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="author_id")
    private int author_id;

    @Column(name="author_contact")
    private String email;

    @Column(name="author_list")
    private String author_list;

    @Column(name="title")
    private String title;

    @Column(name="file_name")
    private String file_name;

    @Column(name="format")
    private String format;

    @Column(name="version")
    private int version;

    @Column(name="state")
    private State state;

    public SubmissionEntity(String email, String title, String file_name,String format, String author_list, int version, int author_id, String c_state){
        this.email = email;
        this.title = title;
        this.file_name = file_name;
        this.format = format;
        this.version = version;
        this.author_list = author_list;
        this.author_id = author_id;
        this.state = State.valueOf(c_state);
    }

    public SubmissionEntity(){

    }

    public String getTitle(){
        return this.title;
    }
    public String getEmail(){
        return this.email;
    }
    public String getFile_name(){
        return this.file_name;
    }
    public String getFormat(){ return this.format; }
    public int getVersion(){
        return this.version;
    }
    public Long getId(){
        return this.id;
    }
    public int getAuthor_id() { return author_id; }
    public String getAuthor_list() { return author_list; }
}

