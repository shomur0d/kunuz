package com.company.Lesson_62.dto;

import com.company.Lesson_62.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private Integer id;
    @NotEmpty(message = "Kalla title qani?")
    private String title;
    @NotEmpty(message = "Kalla content qani?")
    private String content;

    private Integer profileId;
    private Integer regionId;
    // status
    private ProfileRole role;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;

}
