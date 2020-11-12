package com.sam2021.database;

import javax.persistence.*;
import java.beans.ConstructorProperties;

@Entity
@Table(name = "userinfo")
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

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
    public Long getid(){return this.id;}
    public String getRole(){
        return this.role;
    }
    public String getEmail(){
        return this.email;
    }
    public String getName(){
        return this.firstname + " " + this.lastname;
    }
    public String getFirstname() {return this.firstname;}
    public String getLastname() {return this.lastname;}

    public void setRole(String role){this.role = role;};
    public void setEmail(String email){this.email = email;}
    public void setName(String f_name, String l_name){this.firstname = f_name; this.lastname=l_name;}
    public boolean authenticate(String pw){
        return this.pwd.equals(pw);
    }
}
