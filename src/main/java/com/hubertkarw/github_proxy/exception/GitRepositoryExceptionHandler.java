package com.hubertkarw.github_proxy.exception;

import com.hubertkarw.github_proxy.model.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GitRepositoryExceptionHandler {
    @ExceptionHandler(GitRepositoryException.class)
    ResponseEntity<ErrorMessage> handleRepoNotFound(GitRepositoryException exception) {
        return ResponseEntity.status(exception.getStatus()).body(new ErrorMessage(exception.getStatus().value(), exception.getStatus().getReasonPhrase(), exception.getMessage(), exception.getTimestamp()));
    }

}
