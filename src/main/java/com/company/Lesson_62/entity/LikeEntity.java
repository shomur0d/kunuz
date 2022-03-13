package com.company.Lesson_62.entity;

import com.company.Lesson_62.enums.LikeStatus;
import com.company.Lesson_62.enums.LikeType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "likes")
public class LikeEntity extends BaseEntity{

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "profile_id")
        private ProfileEntity profile;

        @Enumerated(EnumType.STRING)
        @Column(name = "status")
        private LikeStatus status;

        private Integer aIdOrCid;

        @Enumerated(EnumType.STRING)
        @Column(name = "type")
        private LikeType type;


}
