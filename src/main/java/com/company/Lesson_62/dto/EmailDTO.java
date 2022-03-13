package com.company.Lesson_62.dto;

import com.company.Lesson_62.enums.EmailStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class EmailDTO {
    private Integer  id;
    private String from_account;
    private String to_account;
    private LocalDateTime created_date;
    private EmailStatus status;
    private LocalDateTime used_date;
}
