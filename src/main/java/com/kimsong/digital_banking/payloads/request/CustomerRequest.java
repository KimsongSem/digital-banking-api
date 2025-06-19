package com.kimsong.digital_banking.payloads.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerRequest {

    private String firstName;

    private String lastName;

    private String gender;

    private String email;

    private String phone;

    private String nationalId;

    private String passportNumber;

    private String occupation;

    private Date dateOfBirth;

    private String nationality;

    private String address;

}
