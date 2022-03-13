package com.company.Lesson_62.service;

import com.company.Lesson_62.dto.LikeDTO;
import com.company.Lesson_62.entity.ArticleEntity;
import com.company.Lesson_62.entity.CommentEntity;
import com.company.Lesson_62.entity.LikeEntity;
import com.company.Lesson_62.entity.ProfileEntity;
import com.company.Lesson_62.exeptions.BadRequestException;
import com.company.Lesson_62.exeptions.ItemNotFoundException;
import com.company.Lesson_62.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private CommentService commentService;

    public LikeDTO create (LikeDTO dto, Integer userId){
        ProfileEntity profile = profileService.get(userId);
        ArticleEntity article = articleService.get(dto.getAidorpid());
        CommentEntity comment = commentService.get(dto.getAidorpid());

        LikeEntity entity = new LikeEntity();
        entity.setProfile(profile);
        entity.setStatus(dto.getStatus());
        entity.setType(dto.getType());
        entity.setAIdOrCid(article.getId());
        entity.setCreatedDate(LocalDateTime.now());

        likeRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public LikeDTO getById(Integer likeId, Integer userId) {
        LikeEntity entity = get(likeId);
        if (!entity.getProfile().getId().equals(userId)) {
            throw new BadRequestException("Not Owner");
        }
        return toDTO(entity);
    }

    public void update(Integer likeId, LikeDTO dto, Integer userId) {
        LikeEntity entity = get(likeId);
        if (!entity.getProfile().getId().equals(userId)) { // owner
            throw new BadRequestException("Not Owner");
        }

        entity.setStatus(dto.getStatus());
        likeRepository.save(entity);
    }

    public void delete(Integer likeId, Integer userId) {
        LikeEntity entity = get(likeId);
        if (!entity.getProfile().getId().equals(userId)) { // owner
            throw new BadRequestException("Not Owner");
        }
        likeRepository.delete(entity);
    }

    public List<LikeDTO> findAll(){
        Iterable<LikeEntity> entityList = likeRepository.findAll();
        Iterator<LikeEntity> iterator = entityList.iterator();
        List<LikeDTO> dtoList = new LinkedList<>();
        while (iterator.hasNext()){
            dtoList.add(toDTO(iterator.next()));
        }
        return dtoList;
    }


    public Integer getCountArticleLike(Integer articleId){
        Optional<Integer> optional = likeRepository.getCountLikeOfArticle(articleId);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public Integer getCountArticleDislike(Integer articleId){
        Optional<Integer> optional = likeRepository.getCountDislikeOfArticle(articleId);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public Integer getCountCommentLike(Integer commentId){
        Optional<Integer> optional = likeRepository.getCountLikeOfComment(commentId);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public Integer getCountCommentDislike(Integer commentId){
        Optional<Integer> optional = likeRepository.getCountDislikeOfComment(commentId);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }





    public LikeEntity get(Integer id) {
        return likeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Comment Not Found"));
    }
    public LikeDTO toDTO (LikeEntity entity){
        LikeDTO dto = new LikeDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setType(entity.getType());
        dto.setProfile(entity.getProfile().getId());
        dto.setAidorpid(entity.getAIdOrCid());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
