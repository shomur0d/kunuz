package com.company.Lesson_62.service;

import com.company.Lesson_62.dto.RegionDTO;
import com.company.Lesson_62.entity.ProfileEntity;
import com.company.Lesson_62.entity.RegionEntity;
import com.company.Lesson_62.enums.ProfileRole;
import com.company.Lesson_62.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private ProfileService profileService;

    public RegionDTO create(RegionDTO dto, Integer id){
        ProfileEntity profile = profileService.get(id);

        RegionEntity entity = new RegionEntity();
        entity.setName(dto.getName());
        entity.setRegion(dto.getRegion());

        regionRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public List<RegionDTO> findAll(){
        Iterable<RegionEntity> entityList = regionRepository.findAll();
        Iterator<RegionEntity> iterator = entityList.iterator();
        List<RegionDTO> dtoList = new LinkedList<>();
        while (iterator.hasNext()){
            dtoList.add(toDTO(iterator.next()));
        }
        return dtoList;
    }

    public RegionDTO getById(Integer id){
        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (optional.isPresent()){
            return toDTO(optional.get());
        }
        return null;
    }

    public RegionDTO UpdateRegionById(Integer id,String name, String region1){
        Optional<RegionEntity> region = regionRepository.findById(id);
        if(!region.isPresent()){
            return null;
        }
        regionRepository.updateRegionById(region1, name, id);
        return toDTO(region.get());
    }

    public String deleteRegionById(Integer id){
        Optional<RegionEntity> region = regionRepository.findById(id);
        if(!region.isPresent()){
            return "not found";
        }
        regionRepository.deleteRegionById(id);
        return "successfully";
    }



    public RegionEntity get(Integer id){
        return regionRepository.findById(id).orElseThrow(()->new RuntimeException("Profile not found"));
    }

    public RegionDTO toDTO (RegionEntity entity){
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRegion(entity.getRegion());
        return dto;
    }
}
