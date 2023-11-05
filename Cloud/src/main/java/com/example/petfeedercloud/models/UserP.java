package com.example.petfeedercloud.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Schedule> schedules;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<PetFeeder> petFeeders;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Pet> pets;
    public UserP(){
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<PetFeeder> getPetFeeders() {
        return petFeeders;
    }

    public void setPetFeeders(List<PetFeeder> petFeeders) {
        this.petFeeders = petFeeders;
    }

    public UserP(Long id, String firstName, String lastName, String email, String password){
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
    }
    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "UserP{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}