package com.hubertkarw.github_proxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timeStamp;

}
