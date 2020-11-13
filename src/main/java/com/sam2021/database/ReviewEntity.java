package com.sam2021.database;

import javax.persistence.*;

/**
 * Represents a review entity.
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>
 * Created: 11/9/20
 * Last Edit: 11/13/20
 */
@Entity
@Table(name="review")
public class ReviewEntity {
    //Attributes
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="reviewer_id")
    private Long reviewer_id;

    @Column(name="paper_id")
    private Long paper_id;

    @Column(name="rating")
    private int rating;

    @Column(name="comments")
    private String comments;

    //constructor
    public ReviewEntity(Long reviewer_id, Long paper_id, int rating, String comments){
        this.reviewer_id = reviewer_id;
        this.paper_id = paper_id;
        this.rating = rating;
        this.comments = comments;
    }

    //getters
    public Long getId() {
        return id;
    }

    public Long getReviewer_id() {
        return reviewer_id;
    }

    public Long getPaper_id() {
        return paper_id;

    }
    public int getRating() {
        return rating;
    }

    public String getComments() {
        return comments;
    }

    //setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setReviewer_id(Long reviewer_id) {
        this.reviewer_id = reviewer_id;
    }
    public void setPaper_id(Long paper_id) {
        this.paper_id = paper_id;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}
