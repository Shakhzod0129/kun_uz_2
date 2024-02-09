package uz.kun.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.ArticleAndItsTypeEntity;

import java.util.List;

public interface ArticleItsTypeRepository extends CrudRepository<ArticleAndItsTypeEntity,Integer> {

    @Transactional
    @Modifying
    @Query("delete from ArticleAndItsTypeEntity where types.id=?1")
    Integer deleteByTypesId(Integer typeId);

    List<ArticleAndItsTypeEntity> findByArticleId(String ArticleId);

    List<ArticleAndItsTypeEntity> findTop5ByTypesIdOrderByCreatedDateDesc(Integer ArticleId);
    List<ArticleAndItsTypeEntity> findByTypesIdOrderByCreatedDateDesc(Integer ArticleId);

    List<ArticleAndItsTypeEntity> findTop4ByTypesIdAndArticleIdNotOrderByCreatedDateDesc(Integer typeId, String articleId);


    void deleteByArticleIdAndTypesId(String articleId, Integer typeId);
}
