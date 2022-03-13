package com.company.Lesson_62.entity;

import com.company.Lesson_62.enums.EmailStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
@ToString
@Setter
@Getter
@Entity
@Table(name = "email")
public class EmailEntity extends BaseEntity{
    @Column
    private String from_account;
    @Column
    private String emailTo;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    @Enumerated(EnumType.STRING)
    private EmailStatus status;
    @Column
    private LocalDateTime used_date;
}
