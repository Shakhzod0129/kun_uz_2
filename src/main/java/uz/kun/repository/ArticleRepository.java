package uz.kun.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uz.kun.entity.ArticleEntity;
import uz.kun.enums.ArticleStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ArticleRepository extends CrudRepository<ArticleEntity,Integer> {

    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible=false where id=?1")
    Integer deleteArticle(String id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set status=?1 WHERE id=?2")
    Integer changeStatus( ArticleStatus status, String id);

    Optional<ArticleEntity>  findById(String id);

    List<ArticleEntity> findTop4ByOrderByViewCountDesc();

    @Query("SELECT a FROM ArticleEntity a WHERE a.status = 'PUBLISHED' and a.id = :articleId ORDER BY a.createdDate DESC")
    ArticleEntity findArticleById(@Param("articleId") String articleId);

    @Query("SELECT a FROM ArticleEntity a WHERE a.status = 'PUBLISHED' and a.regionid = :regionId ORDER BY a.createdDate DESC")
    ArticleEntity findArticleByRegion(@Param("regionId") Integer regionId);


    Page<ArticleEntity> findAllByRegionId(Pageable pageable, Integer regionId);
    Page<ArticleEntity> findAllByCategoryId(Pageable pageable, Integer regionId);
    List<ArticleEntity> findAllByOrderByCreatedDateDesc();


    List<ArticleEntity> findTop8ByOrderByCreatedDateDesc();

    List<ArticleEntity> findTop5ByCategoryIdOrderByCreatedDateDesc(Integer categoryId);


}
