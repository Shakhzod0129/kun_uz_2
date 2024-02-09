package uz.kun.repository;

import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.ArticleAndTagNameEntity;

import java.util.List;
import java.util.Optional;

public interface ArticleAndTagNameRepository extends CrudRepository<ArticleAndTagNameEntity,Long> {


    List<ArticleAndTagNameEntity> findAllByTagsIdOrderByCreatedDateDesc(Long tagId);

    List<ArticleAndTagNameEntity> findAllByArticleId(String articleID);
}
