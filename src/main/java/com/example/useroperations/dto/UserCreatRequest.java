package com.example.useroperations.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class UserCreatRequest {

    private int id;

    @Size(min = 2, max = 15)
    private String firstName;

    @Size(min = 2, max = 15)
    private String lastName;

    @NotBlank
    private String userName;

    @Email
    @ApiModelProperty(notes = "Person Email", example = "aa@gmail.com")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Start date cannot be future time")
    private LocalDate yearOfBirth;
}


