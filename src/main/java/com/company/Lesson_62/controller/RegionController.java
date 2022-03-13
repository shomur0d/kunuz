package com.company.Lesson_62.controller;

import com.company.Lesson_62.dto.ProfileJwtDTO;
import com.company.Lesson_62.dto.RegionDTO;
import com.company.Lesson_62.enums.ProfileRole;
import com.company.Lesson_62.service.RegionService;
import com.company.Lesson_62.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("")
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO dto, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        RegionDTO response = regionService.create(dto, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        List<RegionDTO> response = regionService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        RegionDTO response = regionService.getById(id);
        return ResponseEntity.ok(response);
    }


}
