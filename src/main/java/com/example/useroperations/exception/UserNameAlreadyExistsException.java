package com.example.useroperations.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Error: User Name is already taken!")
public class UserNameAlreadyExistsException extends RuntimeException {
}
