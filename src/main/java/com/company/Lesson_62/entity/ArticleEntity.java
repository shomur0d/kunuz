package com.company.Lesson_62.entity;

import com.company.Lesson_62.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "article")
public class ArticleEntity extends BaseEntity{

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private ProfileEntity publisher;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private RegionEntity regionEntity;

    @Enumerated(EnumType.STRING)
    @Column
    private ArticleStatus status;

    // status
    // profile
    //

}
