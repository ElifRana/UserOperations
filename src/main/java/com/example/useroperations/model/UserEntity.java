package com.example.useroperations.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity //classın bir JPA nesnesi oldugunu belirtiyorum
@Table(name = "user_entities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id //model sınıftaki bir alanı birincil anahtar olarak işaretler
    @GeneratedValue(strategy = GenerationType.AUTO)//H2 de doğrudan desteklenmediği için AUTO kullanıyoruz.
    @Column(name = "id")
    private int id;

    @Size(min = 2, max = 15)
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, max = 15)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank //String'de uzunluğun boş olup olmadığını denetliyor
    @Column(name = "user_name", unique = true)
    private String userName;

    @Email
    @Column(name = "email")
    @ApiModelProperty(notes = "Person Email", example = "aa@gmail.com")
    private String email;

    @Column(name = "actived")
    private boolean actived = true;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Start date cannot be future time")
    @Column(name = "year_of_birth")
    private LocalDate yearOfBirth;

}

