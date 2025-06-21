package com.kimsong.digital_banking.dtos.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String gender;

    private String email;

    @NotBlank
    private String phone;

    private String nationalId;

    private String passportNumber;

    private String occupation;

    @NotNull
    private Date dateOfBirth;

    private String address;

}
