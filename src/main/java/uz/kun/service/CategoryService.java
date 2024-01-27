package uz.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import uz.kun.dto.CategoryDTO;
import uz.kun.dto.CategoryDTO;
import uz.kun.entity.ArticleTypeEntity;
import uz.kun.entity.CategoryEntity;
import uz.kun.entity.RegionEntity;
import uz.kun.exception.AppBadException;
import uz.kun.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CrudService<CategoryDTO, Integer> {
    @Autowired
    public CategoryRepository categoryRepository;


    @Override
    public CategoryDTO create(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();

        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setVisible(true);
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setCreatedDate(LocalDateTime.now());

        categoryRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    @Override
    public CategoryDTO findById(Integer id) {
        return toDTO(get(id));
    }

    @Override
    public Boolean updateById(Integer id, CategoryDTO dto) {
        CategoryEntity entity = get(id);

        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());


        categoryRepository.save(entity);
        return true;
    }

    @Override
    public Boolean deleteById(Integer id) {

        CategoryEntity entity = get(id);

        Integer delete = categoryRepository.delete(entity.getId());

        return true;

    }

    public PageImpl<CategoryDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<CategoryEntity> entityPage = categoryRepository.findAllByVisible(pageable, true);

        List<CategoryEntity> content = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();

        List<CategoryDTO> dtoList = new LinkedList<>();
        for (CategoryEntity entity : content) {
            dtoList.add(toDTO(entity));
        }

        return new PageImpl<>(dtoList, pageable, totalElements);
    }


    public List<CategoryDTO> getByLang(String keyLang) {
            List<CategoryDTO> dtoList=new LinkedList<>();
            Iterable<CategoryEntity> all = categoryRepository.findAll();

            for (CategoryEntity entity : all) {
                CategoryDTO dto = new CategoryDTO();
                dto.setId(entity.getId());
                switch (keyLang) {
                    case "uz" ->dto.setNameUz(entity.getNameUz());
                    case "ru" -> dto.setNameRu(entity.getNameRu());
                    case "en" -> dto.setNameEn(entity.getNameEn());
                    default ->
                            dto.setNameUz( entity.getNameUz());
                };
                dtoList.add(dto);
            }
            return dtoList;
    }


    public CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();

        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setVisible(entity.getVisible());

        return dto;
    }

    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new AppBadException("Category not found"));

    }

}
