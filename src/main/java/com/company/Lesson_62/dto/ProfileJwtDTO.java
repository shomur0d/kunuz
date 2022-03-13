package com.company.Lesson_62.dto;

import com.company.Lesson_62.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileJwtDTO {
    private Integer id;
    private ProfileRole role;
}
