package com.company.Lesson_62.repository;

import com.company.Lesson_62.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer>, JpaSpecificationExecutor<ArticleEntity> {
    @Modifying
    @Transactional
    @Query("update ArticleEntity a set a.content = ?1 where a.title =?2 ")
    int updateArticleByTitle(String content, String title);

    @Modifying
    @Transactional
    @Query(value = "delete from article a where a.title = ?1", nativeQuery = true)
    void deleteArticleByTitle(String title);

    ArticleEntity findByTitle(String title);
}
