package uz.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.entity.ArticleAndItsTypeEntity;
import uz.kun.repository.ArticleItsTypeRepository;
import uz.kun.repository.ArticleTypeRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleAndItsTypeService {

    @Autowired
    private ArticleItsTypeRepository articleItsTypeRepository;


    public void create(String articleId, List<Integer> typesIdList){
        for (Integer typeId : typesIdList) {
            create(articleId,typeId);
        }
    }

    public void create(String articleId, Integer typeId){
        ArticleAndItsTypeEntity entity=new ArticleAndItsTypeEntity();
        entity.setArticleId(articleId);
        entity.setTypesId(typeId);
        articleItsTypeRepository.save(entity);
    }

//    @Transactional
    public void merge(String articleId, List<Integer> newTypeIds) {
        // Eskilar ro'yhati
        List<ArticleAndItsTypeEntity> oldTypes = articleItsTypeRepository.findByArticleId(articleId);

//        Set<Integer> oldIds=new HashSet<>();
//        for (ArticleAndItsTypeEntity oldType : oldTypes) {
//            oldIds.add(oldType.getTypesId());
//        }
        Set<Integer> oldTypeIds = oldTypes.stream()
                .map(ArticleAndItsTypeEntity::getTypesId)
                .collect(Collectors.toSet());

        // Eskilar ro'yhatidan chiqarilgan yangi typelar

//        for (Integer typeId : oldTypeIds) {
//            if (!newTypeIds.contains(typeId)) {
//                toDelete.add(typeId);
//            }

        List<Integer> toDelete = oldTypeIds.stream()
                .filter(id -> !newTypeIds.contains(id))
                .collect(Collectors.toList());

        // Yangi typelarni qo'shish
        for (Integer typeId : newTypeIds) {
            if (!oldTypeIds.contains(typeId)) {
                ArticleAndItsTypeEntity newTypeEntity = new ArticleAndItsTypeEntity();
                newTypeEntity.setArticleId(articleId);
                newTypeEntity.setTypesId(typeId);
                articleItsTypeRepository.save(newTypeEntity);
            }
        }

        // Eskilar ro'yhatidan o'chirish
        for (Integer typeId : toDelete) {
            articleItsTypeRepository.deleteByArticleIdAndTypesId(articleId, typeId);
        }
    }
}

