package com.company.Lesson_62.entity;

import com.company.Lesson_62.enums.ProfileRole;
import com.company.Lesson_62.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@ToString
@Table(name = "profiles")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String login;
    @Column
    private String pswd;
    @Column(unique = true)
    private String email;

    @Column(name = "last_active_date")
    private LocalDateTime lastActiveDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ProfileRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileStatus status;

}
