package uz.kun.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.kun.dto.PaginationResultDTO;
import uz.kun.dto.ProfileFilterDto;
import uz.kun.entity.ProfileEntity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileFilterRepository {

    @Autowired
    private EntityManager entityManager;
    public PaginationResultDTO<ProfileEntity> filter(ProfileFilterDto filterDto, Integer page, Integer size){

        StringBuilder  builder=new StringBuilder();
        Map<String, Object> params=new HashMap<>();

        if (filterDto.getId() != null) {
            builder.append("and id =:id ");
            params.put("id", filterDto.getId());
        }
        if (filterDto.getName() != null) {
            builder.append("and name =:name ");
            params.put("name", filterDto.getName());
        }
        if (filterDto.getSurname() != null) {
            builder.append("and lower(surname) like :surname ");
            params.put("surname", "%" + filterDto.getSurname().toLowerCase() + "%");
        }
        if (filterDto.getRole() != null) {
            builder.append("and role =:role ");
            params.put("role", filterDto.getRole());
        }
        if (filterDto.getEmail() != null) {
            builder.append("and email =:email ");
            params.put("email", filterDto.getEmail());
        }
        if (filterDto.getPhone() != null) {
            builder.append("and phone =:phone ");
            params.put("phone", filterDto.getPhone());
        }
        if (filterDto.getFromDate() != null && filterDto.getToDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filterDto.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filterDto.getToDate(), LocalTime.MAX);
            builder.append("and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filterDto.getFromDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filterDto.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filterDto.getFromDate(), LocalTime.MAX);
            builder.append("and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filterDto.getToDate() != null) {
            LocalDateTime toDate = LocalDateTime.of(filterDto.getToDate(), LocalTime.MAX);
            builder.append("and createdDate <= :toDate ");
            params.put("toDate", toDate);
        }

        StringBuilder selectBuilder=new StringBuilder("from ProfileEntity p where 1=1 ");
        selectBuilder.append(builder);

        StringBuilder countBuilder=new StringBuilder("select count(p) from ProfileEntity p where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult((page-1)*size);
        Query countquery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(),param.getValue());
            countquery.setParameter(param.getKey(),param.getValue());
        }

        List<ProfileEntity> resultList = selectQuery.getResultList();
        Long totalElements = (Long) countquery.getSingleResult();

        return new PaginationResultDTO<>(totalElements,resultList);

    }
}
