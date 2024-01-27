package uz.kun.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.kun.entity.CategoryEntity;
import uz.kun.entity.RegionEntity;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity,Integer>,
        PagingAndSortingRepository<CategoryEntity,Integer> {

    Optional<CategoryEntity> getByIdAndVisible(Integer id, Boolean visible);
    Page<CategoryEntity> findAllByVisible(Pageable pageable,Boolean visible );

    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible=false where id=?1")
    Integer delete(Integer id);
}
