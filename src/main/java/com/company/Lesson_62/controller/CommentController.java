package com.company.Lesson_62.controller;

import com.company.Lesson_62.dto.ArticleFilterDTO;
import com.company.Lesson_62.dto.CommentDTO;
import com.company.Lesson_62.dto.CommentFilterDTO;
import com.company.Lesson_62.dto.ProfileJwtDTO;
import com.company.Lesson_62.service.CommentService;
import com.company.Lesson_62.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    private ResponseEntity create(@Valid @RequestBody CommentDTO dto,
                                  HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        CommentDTO response = commentService.create(dto, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        List<CommentDTO> response = commentService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    private ResponseEntity getById(@PathVariable("id") Integer id,
                                   HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        CommentDTO response = commentService.getById(id, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    private ResponseEntity update(@PathVariable("id") Integer id, @RequestBody CommentDTO dto,
                                  HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        commentService.update(id, dto, jwtDTO.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity delete(@PathVariable("id") Integer id,
                                  HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        commentService.delete(id, jwtDTO.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterSpec(int page, int size, CommentFilterDTO dto, HttpServletRequest request){
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        commentService.filterSpec(page, size, dto);
        return  ResponseEntity.ok().build();
    }


}
