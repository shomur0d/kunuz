package com.company.Lesson_62.repository;

import com.company.Lesson_62.entity.ArticleEntity;
import com.company.Lesson_62.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> , JpaSpecificationExecutor<CommentEntity> {
    Optional<CommentEntity> findById(Integer id);

    @Transactional
    @Modifying
    @Query("Update CommentEntity s set s.content=:content where s.id=:id")
    int updateCommentById(@Param("content") String content, @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = "delete from comments p where p.id = ?1", nativeQuery = true)
    int deleteCommentById(Integer id );

    @Query(value = "select * from comments c where c.profile_id =:pid", nativeQuery = true)
    CommentEntity findAllByProfileId(@Param("pid") Integer profileId);

    @Query(value = "select * from comments c where c.article_id =:pid", nativeQuery = true)
    CommentEntity findAllByArticleId(@Param("pid") Integer articleId);

}
