package com.company.Lesson_62.service;

import com.company.Lesson_62.dto.ArticleDTO;
import com.company.Lesson_62.dto.ArticleFilterDTO;
import com.company.Lesson_62.dto.ProfileDTO;
import com.company.Lesson_62.dto.ProfileFilterDTO;
import com.company.Lesson_62.entity.ArticleEntity;
import com.company.Lesson_62.entity.ProfileEntity;
import com.company.Lesson_62.enums.ArticleStatus;
import com.company.Lesson_62.enums.ProfileStatus;
import com.company.Lesson_62.exeptions.ItemNotFoundException;
import com.company.Lesson_62.repository.ProfileCustomRepositoryImpl;
import com.company.Lesson_62.repository.ProfileRepository;
import com.company.Lesson_62.spec.ArticleSpecification;
import com.company.Lesson_62.spec.ProfileSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileCustomRepositoryImpl profileCustomRepository;

    public ProfileDTO create(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setLogin(dto.getLogin());
        entity.setPswd(dto.getPswd());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());

        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Profile not found"));
    }

    public List<ProfileDTO> findAll() {
        Iterable<ProfileEntity> entityList = profileRepository.findAll();
        Iterator<ProfileEntity> iterator = entityList.iterator();
        List<ProfileDTO> dtoList = new LinkedList<>();
        while (iterator.hasNext()) {
            dtoList.add(toDTO(iterator.next()));
        }
        return dtoList;
    }

    public ProfileDTO getById(Integer id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isPresent()) {
            return toDTO(optional.get());
        }
        return null;
    }

    public ProfileDTO UpdateProfileById(Integer id, String name, String surname) {
        Optional<ProfileEntity> profile = profileRepository.findById(id);
        if (!profile.isPresent()) {
            return null;
        }
        profileRepository.updateProfileById(name, surname, id);
        return toDTO(profile.get());
    }

    public String deleteRegionById(Integer id) {
        Optional<ProfileEntity> profile = profileRepository.findById(id);
        if (!profile.isPresent()) {
            return "not found";
        }
        profileRepository.deleteProfileById(id);
        return "successfully";
    }

    public PageImpl<ProfileDTO> filter(int page, int size, ProfileFilterDTO filterDTO) {
        PageImpl<ProfileEntity> entityPage = profileCustomRepository.filter(page, size, filterDTO);

        List<ProfileDTO> profileDTOList = entityPage.stream().map(articleEntity -> {
            ProfileDTO dto = new ProfileDTO();
            dto.setId(articleEntity.getId());
            //
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(profileDTOList, entityPage.getPageable(), entityPage.getTotalElements());
    }


    public PageImpl<ProfileDTO> filterSpec(int page, int size, ProfileFilterDTO filterDTO){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Specification<ProfileEntity> spec = null;
        if (filterDTO.getStatus() != null) {
            spec = Specification.where(ProfileSpecification.status(filterDTO.getStatus()));
        } else {
            spec = Specification.where(ProfileSpecification.status(ProfileStatus.ACTIVE));
        }

        if (filterDTO.getName() != null) {
            spec.and(ProfileSpecification.name(filterDTO.getName()));
        }
        if (filterDTO.getRole() != null) {
            spec.and(ProfileSpecification.role(filterDTO.getRole()));
        }
        if (filterDTO.getProfileId() != null) {
            spec.and(ProfileSpecification.equal("profile", filterDTO.getProfileId()));
        }

        Page<ProfileEntity> profilePage = profileRepository.findAll(spec, pageable);
        System.out.println(profilePage.getTotalElements());
        return null;
    }


    public ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setLogin(entity.getLogin());
        dto.setEmail(entity.getEmail());
        dto.setPswd(entity.getPswd());
        return dto;
    }

}
