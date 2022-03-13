package com.company.Lesson_62.dto;

import com.company.Lesson_62.enums.ProfileRole;
import com.company.Lesson_62.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    @NotEmpty(message = "Kalla name qani?")
    private String name;
    @NotEmpty(message = "Kalla surname qani?")
    private String surname;
    @Size(min = 3, max = 15, message = "3-15 oralig`ida bo`lishi kerak mazgi.")
    private String login;
    private String pswd;
    @Email
    private String email;
    private ProfileRole role;
    private ProfileStatus status;

    private String jwt; //token
}
