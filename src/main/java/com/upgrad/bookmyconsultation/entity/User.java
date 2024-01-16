package com.upgrad.bookmyconsultation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String emailId;
    private String firstName;
    private String lastName;
    private String dob;
    private String mobile;
    private String password;
    private String createdDate;
    private String salt;
}

