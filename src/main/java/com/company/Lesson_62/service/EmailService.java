package com.company.Lesson_62.service;

import com.company.Lesson_62.dto.EmailDTO;
import com.company.Lesson_62.entity.EmailEntity;
import com.company.Lesson_62.enums.EmailStatus;
import com.company.Lesson_62.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EmailService {
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    private static final String from= "shomurodpro@gmail.com";
    public void sendEmail(String toAccount, String title, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toAccount);
        msg.setSubject(title);
        msg.setText(text);
        javaMailSender.send(msg);

        EmailEntity entity = new EmailEntity();
        entity.setEmailTo(toAccount);
        entity.setFrom_account(from);
        entity.setTitle(title);
        entity.setContent(text);
        save(entity);

    }
    public void save(EmailEntity email){
        EmailEntity entity = new EmailEntity();
        entity.setFrom_account(email.getFrom_account());
        entity.setEmailTo(email.getEmailTo());
        entity.setStatus(EmailStatus.NOTUSED);
        entity.setContent(email.getContent());
        entity.setTitle(email.getTitle());
        entity.setCreatedDate(LocalDateTime.now());
        emailRepository.save(entity);
    }

    public PageImpl<EmailDTO> getAllEmail (int page, int size) {
        Pageable pageList = PageRequest.of(page, size);
        Page<EmailEntity> entityPage = emailRepository.findAll(pageList);

        long totalElements = entityPage.getTotalElements();
        List<EmailEntity> entityList = entityPage.getContent();

        List<EmailDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });

        return new PageImpl<EmailDTO>(dtoList, pageList, totalElements);
    }

    public void changeEmailStatus(String email){
        Optional<EmailEntity> entity = emailRepository.findTopByEmailTo(email);
        if(entity.isPresent()){
            entity.get().setStatus(EmailStatus.USED);
            emailRepository.save(entity.get());
        }
    }

    public EmailDTO getLastSendEmail(){
        Optional<EmailEntity> entity = emailRepository.findTopByOrderByCreatedDate();
        return entity.map(this::toDTO).orElse(null);
    }

    public PageImpl<EmailDTO> getEmailNotUsed (int page, int size) {
        Pageable pageList = PageRequest.of(page, size);
        Page<EmailEntity> entityPage = emailRepository.findAllByStatus(EmailStatus.NOTUSED, pageList);

        long totalElements = entityPage.getTotalElements();
        List<EmailEntity> entityList = entityPage.getContent();

        List<EmailDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return new PageImpl<EmailDTO>(dtoList, pageList, totalElements);
    }

        private EmailDTO toDTO (EmailEntity entity) {
            EmailDTO dto = new EmailDTO();
            dto.setId(entity.getId());
            dto.setStatus(entity.getStatus());
            dto.setCreated_date(entity.getCreatedDate());
            dto.setUsed_date(entity.getUsed_date());
            dto.setFrom_account(entity.getFrom_account());
            dto.setTo_account(entity.getEmailTo());
            return dto;
        }
}
