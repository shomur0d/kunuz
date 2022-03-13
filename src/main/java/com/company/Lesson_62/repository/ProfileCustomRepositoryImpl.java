package com.company.Lesson_62.repository;

import com.company.Lesson_62.dto.ProfileFilterDTO;
import com.company.Lesson_62.entity.ArticleEntity;
import com.company.Lesson_62.entity.ProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepositoryImpl {
    @Autowired
    private EntityManager entityManager;

    public PageImpl filter(int page, int size, ProfileFilterDTO filterDTO) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder builder = new StringBuilder("SELECT a FROM ProfileEntity a ");
        StringBuilder builderCount = new StringBuilder("SELECT count(a) FROM ProfileEntity a ");

        if (filterDTO.getStatus() != null) {
            builder.append("Where status ='" + filterDTO.getStatus().name() + "'");
            builderCount.append("Where status ='" + filterDTO.getStatus().name() + "'");
        } else {
            builder.append("Where status ='ACTIVE'");
            builderCount.append("Where status ='ACTIVE'");
        }

        if (filterDTO.getProfileId() != null) {
            builder.append(" and a.id =:id");
            builderCount.append(" and a.id =:id");
            params.put("id", filterDTO.getProfileId());
        }
        if (filterDTO.getName() != null && !filterDTO.getName().isEmpty()) {
            builder.append(" and a.name =:name");
            builderCount.append(" and a.name =:name");
            params.put("name", filterDTO.getName());
        }
        if (filterDTO.getSurname() != null && !filterDTO.getSurname().isEmpty()) {
            builder.append(" and a.surname =:surname");
            builderCount.append(" and a.surname =:surname");
            params.put("surname", filterDTO.getSurname());
        }
        if (filterDTO.getEmail() != null && !filterDTO.getEmail().isEmpty()) {
            builder.append(" and a.email =:email");
            builderCount.append(" and a.email =:email");
            params.put("email", filterDTO.getEmail());
        }
        if (filterDTO.getRole() != null) {
            builder.append(" and a.role =:role");
            builderCount.append(" and a.role =:role");
            params.put("role", filterDTO.getRole());
        }
        Query query = entityManager.createQuery(builder.toString());
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        for (Map.Entry<String, Object> entrySet : params.entrySet()) {
            query.setParameter(entrySet.getKey(), entrySet.getValue());
        }
        List<ProfileEntity> profileList = query.getResultList();

        query = entityManager.createQuery(builderCount.toString());
        for (Map.Entry<String, Object> entrySet : params.entrySet()) {
            query.setParameter(entrySet.getKey(), entrySet.getValue());
        }
        Long totalCount = (Long) query.getSingleResult();

        return new PageImpl(profileList, PageRequest.of(page, size), totalCount);
    }


    public void filerCriteriaBuilder() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProfileEntity> criteriaQuery = criteriaBuilder.createQuery(ProfileEntity.class);
        Root<ProfileEntity> root = criteriaQuery.from(ProfileEntity.class);

        criteriaQuery.select(root); // select * from ProfileEntity

        Predicate namePred = criteriaBuilder.equal(root.get("name"), "Vali"); // name='Vali'
        //Predicate idPredicate = criteriaBuilder.equal(root.get("id"), 33);

//        Predicate nameAndIdPre = criteriaBuilder.and(namePred, idPredicate);
        //Predicate nameOrIdPre = criteriaBuilder.or(namePred, idPredicate);

        criteriaQuery.where(namePred); // where name='Vali'

        List<ProfileEntity> studentList = entityManager.createQuery(criteriaQuery).getResultList();
        System.out.println(studentList);
    }



}
