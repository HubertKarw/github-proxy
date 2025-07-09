package com.hubertkarw.github_proxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Patient{
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private LocalDate birthday;
}
