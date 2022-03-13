package com.company.Lesson_62.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class CommentFilterDTO {
    private Integer commentId;
    private Integer profileId;
    private Integer articleId;

    private LocalDate fromDate;
    private LocalDate toDate;

}
