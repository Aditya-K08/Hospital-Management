package com.example.MedicalFriend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRequest {
    private String name;

    private String email;

    private String password;

    private LocalDate dateOfBirth;
    private String contactNumber;
    private String address;
}
