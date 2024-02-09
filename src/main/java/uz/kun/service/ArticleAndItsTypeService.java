package uz.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.entity.ArticleAndItsTypeEntity;
import uz.kun.entity.ArticleTypeEntity;
import uz.kun.repository.ArticleItsTypeRepository;
import uz.kun.repository.ArticleTypeRepository;

import java.util.List;
import java.util.Optional;

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

    public void merge(String articleId, List<Integer> typesIdList) {
        // create
        // [] old
        // [1,2,3,4,5] new
        List<ArticleAndItsTypeEntity> entities = selectArticleTypeRepository.findByArticleId(articleId);
//
        int count = 0;
        for (ArticleAndItsTypeEntity entity : entities) {
            if (!typesIdList.contains(entity.getTypesId())) {
                selectArticleTypeRepository.deleteById(entity.getId());
                count++;
            }
        }

        if (count == 0 && entities.size() == typesIdList.size()) {
            return;
        }
        // update
        //[1,2,3,4,5] - old
        //[7,5] - new
        for (Integer typesId : typesIdList) {
            List<ArticleAndItsTypeEntity> articleTypesList = selectArticleTypeRepository.findByTypesIdOrderByCreatedDateDesc(typesId);
            if (articleTypesList.isEmpty()) {
                ArticleAndItsTypeEntity entity = new ArticleAndItsTypeEntity();
                entity.setArticleId(articleId);
                entity.setTypesId(typesId);
                selectArticleTypeRepository.save(entity);
            }
        }
    }
}


//agar berilgan maqola update qilinganda uning article type update qilinsa misol uchun oldin u 1,2,5 typelarda
// bolgan endi u 5,6,7 typlarda update qilindi uning 1,2 typlari uchirilsin qolgani set qilinsin