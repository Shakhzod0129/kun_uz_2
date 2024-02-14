package uz.kun.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<CommentEntity, Long>   {

    Optional<CommentEntity> findByIdAndProfileIDAndArticleId(Long id, Integer profileId,String articleId);

    List<CommentEntity> findByArticleId(String articleId);

    Page<CommentEntity> findAll(Pageable pageable);


}
