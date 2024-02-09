package uz.kun.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.entity.ArticleAndItsTypeEntity;
import uz.kun.entity.ArticleTypeEntity;
import uz.kun.repository.ArticleItsTypeRepository;
import uz.kun.repository.ArticleTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleAndItsTypeService {

    @Autowired
    private ArticleItsTypeRepository selectArticleTypeRepository;
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public void create(String articleId, List<Integer> typesIdList){
        for (Integer typeId : typesIdList) {
            create(articleId,typeId);
        }
    }

    public void create(String articleId, Integer typeId){
        ArticleAndItsTypeEntity entity=new ArticleAndItsTypeEntity();
        entity.setArticleId(articleId);
        entity.setTypesId(typeId);
        selectArticleTypeRepository.save(entity);
    }

    @Transactional
    public void merge(String articleId, List<Integer> newTypeIds) {
        // Eskilar ro'yhati
        List<ArticleAndItsTypeEntity> oldTypes = selectArticleTypeRepository.findByArticleId(articleId);
        Set<Integer> oldTypeIds = oldTypes.stream()
                .map(ArticleAndItsTypeEntity::getTypesId)
                .collect(Collectors.toSet());

        // Eskilar ro'yhatidan chiqarilgan yangi typelar
        List<Integer> toDelete = oldTypeIds.stream()
                .filter(id -> !newTypeIds.contains(id))
                .collect(Collectors.toList());

        // Yangi typelarni qo'shish
        for (Integer typeId : newTypeIds) {
            if (!oldTypeIds.contains(typeId)) {
                ArticleAndItsTypeEntity newTypeEntity = new ArticleAndItsTypeEntity();
                newTypeEntity.setArticleId(articleId);
                newTypeEntity.setTypesId(typeId);
                selectArticleTypeRepository.save(newTypeEntity);
            }
        }

        // Eskilar ro'yhatidan o'chirish
        for (Integer typeId : toDelete) {
            selectArticleTypeRepository.deleteByArticleIdAndTypesId(articleId, typeId);
        }
    }
}

