package com.hubertkarw.github_proxy.service;

import com.hubertkarw.github_proxy.feignClient.MedicalClinicClient;
import com.hubertkarw.github_proxy.model.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class MedicalClinicService {
    private final MedicalClinicClient medClient;

    //    medical clinic
    public List<Patient> getPatients() {
        return medClient.getPatients();
    }

    //    medical clinic
    public Patient getPatient(String email) {
        return medClient.getPatient(email);
    }

}
