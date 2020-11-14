package com.sam2021.database;

import javax.persistence.*;

@Entity
@Table(name="submission")
public class SubmissionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="author_id")
    private Long authorId;

    @Column(name="author_contact")
    private String email;

    @Column(name="author_list")
    private String authorList;

    @Column(name="title")
    private String title;

    @Column(name="file_name")
    private String fileName;

    @Column(name="format")
    private String format;

    @Column(name="version")
    private int version;
    //States are: SUBMITTED, REVIEWING, REVIEWED, RELEASED,
    @Column(name="c_state")
    private String cstate;

    public SubmissionEntity(String email, String title, String file_name,String format, String author_list, int version, Long author_id, String cstate){
        this.email = email;
        this.title = title;
        this.fileName = file_name;
        this.format = format;
        this.version = version;
        this.authorList = author_list;
        this.authorId = author_id;
        this.cstate = cstate;
    }

    public SubmissionEntity(){

    }
    //Getters
    public String getTitle(){
        return this.title;
    }
    public String getEmail(){
        return this.email;
    }
    public String getFileName(){
        return this.fileName;
    }
    public String getFormat(){ return this.format; }
    public String getCstate() {return this.cstate;}
    public int getVersion(){ return this.version;}
    public Long getId(){ return this.id; }
    public Long getAuthor_id() { return authorId; }
    public String getAuthor_list() { return authorList; }

    //Setters
    public void setCstate(String state){this.cstate = state;}
}
