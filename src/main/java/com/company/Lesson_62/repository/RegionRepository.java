package com.company.Lesson_62.repository;

import com.company.Lesson_62.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface RegionRepository extends JpaRepository<RegionEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update RegionEntity r set r.region = ?1, r.name = ?1 where r.id =?2 ")
    int updateRegionById(String region, String name, Integer id);

    @Modifying
    @Transactional
    @Query(value = "delete from region r where r.id = ?1", nativeQuery = true)
    void deleteRegionById(Integer id);

}
