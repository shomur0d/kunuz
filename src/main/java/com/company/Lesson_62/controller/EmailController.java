package com.company.Lesson_62.controller;

import com.company.Lesson_62.dto.EmailDTO;
import com.company.Lesson_62.dto.ProfileJwtDTO;
import com.company.Lesson_62.enums.ProfileRole;
import com.company.Lesson_62.service.EmailService;
import com.company.Lesson_62.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public ResponseEntity<?> getAllEmail(@RequestParam("page") int page, @RequestParam("size") int size,
                                         HttpServletRequest request) {
        ProfileJwtDTO profileJwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        PageImpl<EmailDTO> articleDTOList = emailService.getAllEmail(page,size);
        return ResponseEntity.ok(articleDTOList);
    }

    @GetMapping("/notused")
    public ResponseEntity<?> getEmailNotUsed(@RequestParam("page") int page, @RequestParam("size") int size,
                                             HttpServletRequest request) {
        ProfileJwtDTO profileJwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        PageImpl<EmailDTO> articleDTOList = emailService.getEmailNotUsed(page, size);
        return ResponseEntity.ok(articleDTOList);
    }

    @GetMapping("last")
    public ResponseEntity<?> getLastSendEmail(HttpServletRequest request) {
        ProfileJwtDTO profileJwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        EmailDTO articleDTOList = emailService.getLastSendEmail();
        return ResponseEntity.ok(articleDTOList);
    }
}
