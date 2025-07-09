package com.hubertkarw.github_proxy.controller;

import com.hubertkarw.github_proxy.model.Patient;
import com.hubertkarw.github_proxy.service.GitRepositoryService;
import com.hubertkarw.github_proxy.service.MedicalClinicService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@AllArgsConstructor
@RequiredArgsConstructor
@RequestMapping("/medical")
public class PatientController {
    private final MedicalClinicService service;

    @GetMapping("/patients")
    public List<Patient> getPatients() {
        return service.getPatients();
    }

    @GetMapping("/patients/{email}")
    public Patient getPatient(@PathVariable("email") String email) {
        return service.getPatient(email);
    }
}
