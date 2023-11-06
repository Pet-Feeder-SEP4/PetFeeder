package com.example.petfeedercloud.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId") // Name of the foreign key column in the pet_feeder table
    private UserP user;

    private Long petFeederId;

    private String name;
    private Date birthdate;
    private int weight;
    private String breed;

    public Pet() {

    }
    public UserP getUser() {
        return user;
    }
    public void setUser(UserP user) {
        this.user = user;
    }

    public Long getPetFeederId() {
        return petFeederId;
    }

    public void setPetFeederId(Long petFeederId) {
        this.petFeederId = petFeederId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Long getPetId() {
        return petId;
    }
}
