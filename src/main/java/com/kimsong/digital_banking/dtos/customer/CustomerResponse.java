package com.kimsong.digital_banking.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse implements Serializable {
//    private Long id;

    private String firstName;

    private String lastName;

//    private String phone;
//
//    private String gender;
//
//    private String email;
//
//    private String nationalId;
//
//    private String passportNumber;
//
//    private String occupation;
//
//    private Date dateOfBirth;
//
//    private String address;

}
