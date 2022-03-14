package com.company.Lesson_62.controller;

import com.company.Lesson_62.dto.ArticleDTO;
import com.company.Lesson_62.dto.ArticleFilterDTO;
import com.company.Lesson_62.dto.ProfileJwtDTO;
import com.company.Lesson_62.enums.ProfileRole;
import com.company.Lesson_62.service.ArticleService;
import com.company.Lesson_62.util.JwtUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /*@PostMapping("")
    public ResponseEntity<ArticleDTO> create(@RequestBody ArticleDTO dto,
                                             @RequestHeader("userId") Integer userId) {
        String jwt = token.split(" ")[1];
        Integer userId = JwtUtil.decodeJwt(jwt);

        ArticleDTO response = articleService.create(dto, userId);
        return ResponseEntity.ok(response);
    }*/

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ArticleDTO dto,
                                    HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.MODERATOR_ROLE);
        ArticleDTO response = articleService.create(dto, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/publish/{id}")
    public ResponseEntity<?> publish(@ApiParam(value = "id", readOnly = true,example = "id of article")
            @PathVariable("id") Integer id,
                                     HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.PUBLISHER_ROLE);
        articleService.publish(id, jwtDTO.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterSpec(int page, int size, ArticleFilterDTO dto, HttpServletRequest request){
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        articleService.filterSpec(page, size, dto);
        return  ResponseEntity.ok().build();
    }


    @GetMapping("")
    public ResponseEntity<?> getAll(){
        List<ArticleDTO> response = articleService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        ArticleDTO response = articleService.getById(id);
        return ResponseEntity.ok(response);
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        ArticleDTO response = articleService.getById(id);
        return ResponseEntity.ok(response);
    }*/


    /*@GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        ArticleDTO response = articleService.getById(id);
        return ResponseEntity.ok(response);
    }*/
    /*@GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        ArticleDTO response = articleService.getById(id);
        return ResponseEntity.ok(response);
    }*/

}
