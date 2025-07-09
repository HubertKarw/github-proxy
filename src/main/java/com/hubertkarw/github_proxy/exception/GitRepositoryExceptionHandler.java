package com.hubertkarw.github_proxy.exception;

import com.hubertkarw.github_proxy.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GitRepositoryExceptionHandler {
    @ExceptionHandler(GitRepositoryException.class)
    ResponseEntity<ErrorMessage> handleRepoNotFound(GitRepositoryException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(new ErrorMessage(exception.getStatus().value(), exception.getStatus().getReasonPhrase(), exception.getMessage(), exception.getTimestamp()));
    }

}
