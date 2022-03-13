package com.company.Lesson_62.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Integer id;
    @NotEmpty(message = "Kalla content qani?")
    private String content;

    private LocalDateTime createdDate;
    private Integer profileId;
    @NotEmpty(message = "Kalla articleId qani?")
    private Integer articleId;
}
