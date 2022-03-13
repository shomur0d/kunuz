package com.company.Lesson_62.repository;

import com.company.Lesson_62.entity.ArticleEntity;
import com.company.Lesson_62.entity.ProfileEntity;
import com.company.Lesson_62.service.AuthService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer>, JpaSpecificationExecutor<ProfileEntity> {

    Optional<ProfileEntity> findByLoginAndPswd(String login, String password);

    @Transactional
    @Modifying
    @Query("Update ProfileEntity s set s.name=:name, s.surname=:surname where s.id=:id")
    int updateProfileById(@Param("name") String name, @Param("surname") String surname, @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = "delete from profiles p where p.id = ?1", nativeQuery = true)
    int deleteProfileById(Integer id );


     Optional<ProfileEntity> findByEmail(String email);
     Optional<ProfileEntity> findByLogin(String login);

}
