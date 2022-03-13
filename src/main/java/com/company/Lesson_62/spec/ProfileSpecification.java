package com.company.Lesson_62.spec;

import com.company.Lesson_62.entity.ArticleEntity;
import com.company.Lesson_62.entity.ProfileEntity;
import com.company.Lesson_62.enums.ProfileRole;
import com.company.Lesson_62.enums.ProfileStatus;
import org.springframework.data.jpa.domain.Specification;

public class ProfileSpecification {
    public static Specification<ProfileEntity> status(ProfileStatus status) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"), status);
        });
    }

    public static Specification<ProfileEntity> role(ProfileRole role) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("role"), role);
        });
    }

    public static Specification<ProfileEntity> equal(String field, Integer id) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(field), id);
        });
    }

    public static Specification<ProfileEntity> name(String name) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("name"), name);
        });
    }
}
