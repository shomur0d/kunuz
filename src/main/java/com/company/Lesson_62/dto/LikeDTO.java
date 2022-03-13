package com.company.Lesson_62.dto;

import com.company.Lesson_62.entity.ProfileEntity;
import com.company.Lesson_62.enums.LikeStatus;
import com.company.Lesson_62.enums.LikeType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LikeDTO {
    private Integer id;

    private Integer profile;

    private LikeStatus status;

    private Integer aidorpid;
    private LikeType type;
    private LocalDateTime createdDate;
}
