package com.company.Lesson_62.controller;

import com.company.Lesson_62.dto.CommentDTO;
import com.company.Lesson_62.dto.LikeDTO;
import com.company.Lesson_62.dto.ProfileJwtDTO;
import com.company.Lesson_62.service.LikeService;
import com.company.Lesson_62.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping
    private ResponseEntity create(@RequestBody LikeDTO dto, HttpServletRequest request){
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        LikeDTO response = likeService.create(dto, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        List<LikeDTO> response = likeService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    private ResponseEntity getById(@PathVariable("id") Integer id,
                                   HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        LikeDTO response = likeService.getById(id, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    private ResponseEntity update(@PathVariable("id") Integer id, @RequestBody LikeDTO dto,
                                  HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        likeService.update(id, dto, jwtDTO.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity delete(@PathVariable("id") Integer id,
                                  HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        likeService.delete(id, jwtDTO.getId());
        return ResponseEntity.ok().build();
    }


}
