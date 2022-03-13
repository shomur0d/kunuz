package com.company.Lesson_62.repository;

import com.company.Lesson_62.entity.EmailEntity;
import com.company.Lesson_62.enums.EmailStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<EmailEntity, Integer> {

    Optional<EmailEntity> findTopByEmailTo(String emailTo);

    Optional<EmailEntity> findTopByOrderByCreatedDate();

    Page<EmailEntity> findAllByStatus(EmailStatus status, Pageable pageable);
}
