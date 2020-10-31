package com.sam2021.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userinfo")
public class UserEntity {

    @Id
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

    public UserEntity(String email, String password, String firstname, String lastname, String role){
        this.email = email;
        this.pwd = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }

    public UserEntity(){

    }
}
