package com.company.Lesson_62.service;

import com.company.Lesson_62.dto.*;
import com.company.Lesson_62.entity.ArticleEntity;
import com.company.Lesson_62.entity.CommentEntity;
import com.company.Lesson_62.entity.ProfileEntity;
import com.company.Lesson_62.enums.ProfileStatus;
import com.company.Lesson_62.exeptions.BadRequestException;
import com.company.Lesson_62.exeptions.ItemNotFoundException;
import com.company.Lesson_62.repository.CommentCustomRepositoryImpl;
import com.company.Lesson_62.repository.CommentRepository;
import com.company.Lesson_62.spec.CommentSpecification;
import com.company.Lesson_62.spec.ProfileSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentCustomRepositoryImpl commentCustomRepository;

    public CommentDTO create(CommentDTO dto, Integer userId){
        ProfileEntity profile = profileService.get(userId);
        ArticleEntity article = articleService.get(dto.getArticleId());

        if (dto.getContent() == null || dto.getContent().isEmpty()){
            throw new BadRequestException("Content can not be null");
        }

        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setProfile(profile);
        entity.setArticle(article);
        entity.setCreatedDate(LocalDateTime.now());

        commentRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }


    public CommentDTO getById(Integer commentId, Integer userId) {
        CommentEntity entity = get(commentId);
        if (!entity.getProfile().getId().equals(userId)) {
            throw new BadRequestException("Not Owner");
        }
        return toDTO(entity);
    }

    public void update(Integer commentId, CommentDTO dto, Integer userId) {
        CommentEntity entity = get(commentId);
        if (!entity.getProfile().getId().equals(userId)) { // owner
            throw new BadRequestException("Not Owner");
        }

        entity.setContent(dto.getContent());
        commentRepository.save(entity);
    }

    public void delete(Integer commentId, Integer userId) {
        CommentEntity entity = get(commentId);
        if (!entity.getProfile().getId().equals(userId)) { // owner
            throw new BadRequestException("Not Owner");
        }
        commentRepository.delete(entity);
    }


    public List<CommentDTO> findAll(){
        Iterable<CommentEntity> entityList = commentRepository.findAll();
        Iterator<CommentEntity> iterator = entityList.iterator();
        List<CommentDTO> dtoList = new LinkedList<>();
        while (iterator.hasNext()){
            dtoList.add(toDTO(iterator.next()));
        }
        return dtoList;
    }

    public PageImpl<CommentDTO> filter(int page, int size, CommentFilterDTO filterDTO) {
        PageImpl<CommentEntity> entityPage = commentCustomRepository.filter(page, size, filterDTO);

        List<CommentDTO> commentDTOList = entityPage.stream().map(articleEntity -> {
            CommentDTO dto = new CommentDTO();
            dto.setId(articleEntity.getId());
            //
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(commentDTOList, entityPage.getPageable(), entityPage.getTotalElements());
    }


    public PageImpl<CommentDTO> filterSpec(int page, int size, CommentFilterDTO filterDTO){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Specification<CommentEntity> spec = null;
        if (filterDTO.getArticleId() != null) {
            spec = Specification.where(CommentSpecification.equal("article.id", filterDTO.getArticleId()));
        } else {
            spec = Specification.where(CommentSpecification.equal("article.id", 1));
        }

        if (filterDTO.getProfileId() != null) {
            spec.and(CommentSpecification.equal("profile.id", filterDTO.getProfileId()));
        }
        if (filterDTO.getProfileId() != null) {
            spec.and(CommentSpecification.equal("comment.id", filterDTO.getCommentId()));
        }

        Page<CommentEntity> commentPage = commentRepository.findAll(spec, pageable);
        System.out.println(commentPage.getTotalElements());
        return null;
    }


    public CommentEntity get(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Comment Not Found"));
    }
    public CommentDTO toDTO (CommentEntity entity){
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setProfileId(entity.getProfile().getId());
        dto.setArticleId(entity.getArticle().getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
