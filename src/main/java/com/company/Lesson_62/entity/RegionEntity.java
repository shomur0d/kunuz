package com.company.Lesson_62.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "region")
public class RegionEntity extends BaseEntity{
    private String name;
    private String region;
}
