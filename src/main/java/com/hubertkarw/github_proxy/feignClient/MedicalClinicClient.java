package com.hubertkarw.github_proxy.feignClient;

import com.hubertkarw.github_proxy.config.FeignClientConfig;
import com.hubertkarw.github_proxy.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "medical", configuration = FeignClientConfig.class)
public interface MedicalClinicClient {

    @GetMapping("/patients")
    List<Patient> getPatients();

    @GetMapping("/patients/{email}")
    Patient getPatient(
            @PathVariable("email") String email
    );
}
