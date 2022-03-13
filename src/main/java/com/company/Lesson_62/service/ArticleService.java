package com.company.Lesson_62.service;

import com.company.Lesson_62.dto.ArticleDTO;
import com.company.Lesson_62.dto.ArticleFilterDTO;
import com.company.Lesson_62.entity.ArticleEntity;
import com.company.Lesson_62.entity.ProfileEntity;
import com.company.Lesson_62.entity.RegionEntity;
import com.company.Lesson_62.enums.ArticleStatus;
import com.company.Lesson_62.exeptions.BadRequestException;
import com.company.Lesson_62.exeptions.ItemNotFoundException;
import com.company.Lesson_62.repository.ArticleCustomRepositoryImpl;
import com.company.Lesson_62.repository.ArticleRepository;
import com.company.Lesson_62.spec.ArticleSpecification;
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
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ArticleCustomRepositoryImpl articleCustomRepository;

    public ArticleDTO create(ArticleDTO dto, Integer userId) {
        ProfileEntity profile = profileService.get(userId);
        RegionEntity region = regionService.get(dto.getRegionId());

        if (dto.getTitle() == null || dto.getTitle().isEmpty()){
            throw new BadRequestException("Title can not be null");
        }
        if (dto.getContent() == null || dto.getContent().isEmpty()){
            throw new BadRequestException("Content can not be null");
        }

        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setProfile(profile);
        entity.setRegionEntity(region);

        articleRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }


    public void publish(Integer articleId, Integer userId) {
        ProfileEntity profileEntity = profileService.get(userId);

        ArticleEntity entity = get(articleId);
        entity.setStatus(ArticleStatus.PUBLISHED);
        entity.setPublishedDate(LocalDateTime.now());
        entity.setPublisher(profileEntity);
        articleRepository.save(entity);
    }

    public ArticleEntity get(Integer id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Article not found"));
    }


    public List<ArticleDTO> findAll(){
        Iterable<ArticleEntity> entityList = articleRepository.findAll();
        Iterator<ArticleEntity> iterator = entityList.iterator();
        List<ArticleDTO> dtoList = new LinkedList<>();
        while (iterator.hasNext()){
            dtoList.add(toDTO(iterator.next()));
        }
        return dtoList;
    }

    public ArticleDTO getById(Integer id){
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isPresent()){
            return toDTO(optional.get());
        }
        return null;
    }

    public String updateArticleByName(String content, String title) {
        Optional<ArticleEntity> article = Optional.ofNullable(articleRepository.findByTitle(title));
        if (!article.isPresent()) {
            return "not found";
        }
        articleRepository.updateArticleByTitle(content, title);
        return "successfully";
    }

    public String deleteArticleByName(String title) {
        Optional<ArticleEntity> article = Optional.ofNullable(articleRepository.findByTitle(title));
        if (!article.isPresent()) {
            return "not found";
        }
        articleRepository.deleteArticleByTitle(title);
        return "successfully";
    }


    public PageImpl<ArticleDTO> filter(int page, int size, ArticleFilterDTO filterDTO) {
        PageImpl<ArticleEntity> entityPage = articleCustomRepository.filter(page, size, filterDTO);

        List<ArticleDTO> articleDTOList = entityPage.stream().map(articleEntity -> {
            ArticleDTO dto = new ArticleDTO();
            dto.setId(articleEntity.getId());
            //
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(articleDTOList, entityPage.getPageable(), entityPage.getTotalElements());
    }

    public PageImpl<ArticleDTO> filterSpe(int page, int size, ArticleFilterDTO filterDTO){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        Specification<ArticleEntity> title = ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("title"), filterDTO.getTitle());
        });

        Specification<ArticleEntity> idSpec = ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("id"), filterDTO.getArticleId());
        });

        Specification<ArticleEntity> spec = Specification.where(title);
        spec.and(idSpec);

        Page<ArticleEntity> articlePage = articleRepository.findAll(spec, pageable);
        System.out.println(articlePage.getTotalElements());
        return null;
    }


    public PageImpl<ArticleDTO> filterSpec(int page, int size, ArticleFilterDTO filterDTO){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Specification<ArticleEntity> spec = null;
        if (filterDTO.getStatus() != null) {
            spec = Specification.where(ArticleSpecification.status(filterDTO.getStatus()));
        } else {
            spec = Specification.where(ArticleSpecification.status(ArticleStatus.PUBLISHED));
        }

        if (filterDTO.getTitle() != null) {
            spec.and(ArticleSpecification.title(filterDTO.getTitle()));
        }
        if (filterDTO.getArticleId() != null) {
            spec.and(ArticleSpecification.equal("id", filterDTO.getArticleId()));
        }
        if (filterDTO.getProfileId() != null) {
            spec.and(ArticleSpecification.equal("profile.id", filterDTO.getProfileId()));
        }
        List<ArticleDTO> dtoList = new LinkedList<>();
        Page<ArticleEntity> articlePage = articleRepository.findAll(spec, pageable);
        System.out.println(articlePage.getTotalElements());
        return null;
    }


    public ArticleDTO toDTO (ArticleEntity entity){
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setProfileId(entity.getProfile().getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setRegionId(entity.getRegionEntity().getId());
        return dto;
    }

}
