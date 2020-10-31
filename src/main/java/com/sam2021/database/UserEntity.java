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

    @Column(name="firstName")
    private String firstname;

    @Column(name="lastName")
    private String lastname;

    public UserEntity(Integer id, String firstname, String lastname){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public UserEntity(){

    }
}
