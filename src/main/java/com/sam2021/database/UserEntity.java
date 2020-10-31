package com.sam2021.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userinfo")
public class UserEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name="email")
    private String email;

    @Column(name="pwd")
    private String pwd;

    @Column(name="firstName")
    private String firstname;

    @Column(name="lastName")
    private String lastname;

    @Column(name="role")
    private String role;

    public UserEntity(Integer id, String firstname, String lastname, String email, String pwd, String role){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.pwd = pwd;
        this.role = role;
    }

    public UserEntity(){

    }
}
