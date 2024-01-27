package uz.kun.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import uz.kun.entity.ArticleTypeEntity;

import java.util.List;
import java.util.Optional;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer>,
        PagingAndSortingRepository<ArticleTypeEntity,Integer> {

    Optional<ArticleTypeEntity>  getByIdAndVisible(Integer id, Boolean visible);

    Page<ArticleTypeEntity> findAllByVisible(Pageable pageable,Boolean visible);

    List<ArticleTypeEntity> findAllByVisible(Boolean visible);

     @Transactional
    @Modifying
    @Query("update ArticleTypeEntity set visible=false where id=:id")
    Integer delete(@Param("id") Integer id);

}
