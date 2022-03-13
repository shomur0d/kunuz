package com.company.Lesson_62.dto;

import com.company.Lesson_62.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ArticleFilterDTO {
    private String title;
    private Integer profileId;
    private Integer articleId;
    private ArticleStatus status;

    private LocalDate fromDate;
    private LocalDate toDate;
}
