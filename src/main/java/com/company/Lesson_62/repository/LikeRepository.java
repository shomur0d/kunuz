package com.company.Lesson_62.repository;

import com.company.Lesson_62.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {

    @Query(value = "select count (id) from like p where p.aIdOrCid=:aid and p.type ='ARTICLE' and p.status = 'LIKE'", nativeQuery = true)
    Optional<Integer> getCountLikeOfArticle(@Param("aid") Integer articleId);

    @Query(value = "select count (id) from like p where p.aIdOrCid=:aid and p.type ='ARTICLE' and p.status = 'DISLIKE'", nativeQuery = true)
    Optional<Integer> getCountDislikeOfArticle(@Param("aid") Integer articleId);

    @Query(value = "select count (id) from like p where p.aIdOrCid=:aid and p.type ='COMMENT' and p.status = 'LIKE'", nativeQuery = true)
    Optional<Integer> getCountLikeOfComment(@Param("aid") Integer commentId);

    @Query(value = "select count (id) from like p where p.aIdOrCid=:aid and p.type ='COMMENT' and p.status = 'DISLIKE'", nativeQuery = true)
    Optional<Integer> getCountDislikeOfComment(@Param("aid") Integer commentId);

}
