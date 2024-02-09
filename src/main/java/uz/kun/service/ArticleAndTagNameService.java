package uz.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.entity.ArticleAndTagNameEntity;
import uz.kun.repository.ArticleAndTagNameRepository;

import java.util.List;

@Service
public class ArticleAndTagNameService {

    @Autowired
    private ArticleAndTagNameRepository articleAndTagNameRepository;

    public void create(String articleId, List<Long> typesIdList) {
        for (Long typeId : typesIdList) {
            create(articleId, typeId);
        }
    }

    public void create(String articleId, Long tagId) {
        ArticleAndTagNameEntity entity = new ArticleAndTagNameEntity();
        entity.setArticleId(articleId);
        entity.setTagsId(tagId);
        articleAndTagNameRepository.save(entity);
    }

}
