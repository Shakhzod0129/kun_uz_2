package uz.kun.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import uz.kun.entity.RegionEntity;

import java.util.Optional;

public interface RegionRepository extends CrudRepository<RegionEntity,Integer>,
        PagingAndSortingRepository<RegionEntity,Integer> {

    Optional<RegionEntity> getByIdAndVisible(Integer id, Boolean visible);
    Page<RegionEntity> findAllByVisible(Pageable pageable,Boolean visible );

    @Transactional
    @Modifying
    @Query("update RegionEntity set visible=false where id=:id")
    Integer delete(@Param("id") Integer id);
}
