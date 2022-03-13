package com.company.Lesson_62.repository;

import com.company.Lesson_62.dto.CommentFilterDTO;
import com.company.Lesson_62.entity.ArticleEntity;
import com.company.Lesson_62.entity.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentCustomRepositoryImpl {
    @Autowired
    EntityManager entityManager;

    public PageImpl filter(int page, int size, CommentFilterDTO filterDTO) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder builder = new StringBuilder("SELECT a FROM CommentEntity a ");
        StringBuilder builderCount = new StringBuilder("SELECT count(a) FROM CommentEntity a ");

        if (filterDTO.getArticleId() != null) {
            builder.append("Where article_id ='" + filterDTO.getArticleId() + "'");
            builderCount.append("Where article_id ='" + filterDTO.getArticleId() + "'");
        } else {
            builder.append("Where article_id = 1 ");
            builderCount.append("Where article_id = 1 ");
        }
        if (filterDTO.getProfileId() != null) {
            builder.append(" and a.id =:id");
            builderCount.append(" and a.id =:id");
            params.put("id", filterDTO.getProfileId());
        }
        if (filterDTO.getCommentId() != null) {
            builder.append(" and a.id =:id");
            builderCount.append(" and a.id =:id");
            params.put("id", filterDTO.getCommentId());
        }
        if (filterDTO.getFromDate() != null) {
            builder.append(" and a.createdDate >=:fromDate");
            builderCount.append(" and a.createdDate >=:fromDate");
            params.put("fromDate", LocalDateTime.of(filterDTO.getFromDate(), LocalTime.MIN));
        }
        if (filterDTO.getToDate() != null) {
            builder.append(" and a.createdDate <=:toDate");
            builderCount.append(" and a.createdDate <=:toDate");
            params.put("toDate", LocalDateTime.of(filterDTO.getToDate(), LocalTime.MAX));
        }
        Query query = entityManager.createQuery(builder.toString());
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        for (Map.Entry<String, Object> entrySet : params.entrySet()) {
            query.setParameter(entrySet.getKey(), entrySet.getValue());
        }
        List<CommentEntity> commentList = query.getResultList();


        query = entityManager.createQuery(builderCount.toString());
        for (Map.Entry<String, Object> entrySet : params.entrySet()) {
            query.setParameter(entrySet.getKey(), entrySet.getValue());
        }
        Long totalCount = (Long) query.getSingleResult();

        return new PageImpl(commentList, PageRequest.of(page, size), totalCount);
    }

}
