package com.company.Lesson_62.dto;

import com.company.Lesson_62.enums.ProfileRole;
import com.company.Lesson_62.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ProfileFilterDTO {
    private Integer profileId;
    private String name;
    private String surname;
    private String email;
    private ProfileRole role;
    private ProfileStatus status;


}
