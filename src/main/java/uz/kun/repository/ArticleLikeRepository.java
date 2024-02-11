package uz.kun.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.ArticleLikeEntity;

import java.util.Optional;

public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity,Long> {

    Optional<ArticleLikeEntity> findByArticleIdAndProfileId(String articleId, Integer profileId);

//    @Transactional
//    @Modifying
//    @Query("delete from ArticleLikeEntity a where a.articleId=?1 and a.profileId=?2")
//
//    Integer delete

    @Transactional
    @Modifying
    void deleteByArticleIdAndProfileId(String articleId, Integer profileId);
}
