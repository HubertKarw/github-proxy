package com.hubertkarw.github_proxy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class GitRepositoryException extends RuntimeException {

    private LocalDateTime timestamp;
    private HttpStatus status;

    public GitRepositoryException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
