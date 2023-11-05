package com.example.petfeedercloud.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    public String firstName;
    public String lastName;
    public String email;
    public String password;



    public UserP(){
    }
    public UserP(Long userId, String firstName, String lastName, String email, String password){
        this.userId=userId;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
    }
    public Long getUserId() {
        return userId;
    }
    public void setuserId(Long userId) {
        this.userId = userId;
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
                "id=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}