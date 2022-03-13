package com.company.Lesson_62.spec;

import com.company.Lesson_62.entity.CommentEntity;
import org.springframework.data.jpa.domain.Specification;

public class CommentSpecification {

    public static Specification<CommentEntity> equal(String field, Integer id) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(field), id);
        });
    }

}
