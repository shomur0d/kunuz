package com.company.Lesson_62.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class RegistrationDTO {
    @NotEmpty(message = "Kalla name qani?")
    private String name;
    @NotEmpty(message = "Kalla surname qani?")
    @Size(min = 3, max = 15, message = "3-15 oralig`ida bo`lishi kerak mazgi.")
    private String surname;
    private String login;
    private String pswd;
    @Email
    private String email;
}
