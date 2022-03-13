package com.company.Lesson_62.controller;

import com.company.Lesson_62.dto.ArticleFilterDTO;
import com.company.Lesson_62.dto.ProfileDTO;
import com.company.Lesson_62.dto.ProfileFilterDTO;
import com.company.Lesson_62.dto.ProfileJwtDTO;
import com.company.Lesson_62.enums.ProfileRole;
import com.company.Lesson_62.service.ProfileService;
import com.company.Lesson_62.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;


    @PostMapping("")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileDTO dto, HttpServletRequest request) {

        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);

        ProfileDTO response = profileService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        List<ProfileDTO> response = profileService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        ProfileDTO response = profileService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterSpec(int page, int size, ProfileFilterDTO dto, HttpServletRequest request){
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        profileService.filterSpec(page, size, dto);
        return  ResponseEntity.ok().build();
    }


    // registration
    // authorization
}
