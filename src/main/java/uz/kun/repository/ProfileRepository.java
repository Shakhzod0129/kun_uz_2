package uz.kun.repository;

import jakarta.transaction.Transactional;
import jdk.jfr.TransitionFrom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.kun.entity.ProfileEntity;
import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.RegionEntity;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByEmailAndPassword(String email, String password);

    Page<ProfileEntity> findAllByVisible(Pageable pageable, boolean b);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible=false where id=:id")
    Integer delete(@Param("id") Integer id);
}
