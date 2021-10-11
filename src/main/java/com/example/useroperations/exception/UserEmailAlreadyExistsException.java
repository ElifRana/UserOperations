package com.example.useroperations.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Error: Email is already taken!")
public class UserEmailAlreadyExistsException extends RuntimeException {

}
