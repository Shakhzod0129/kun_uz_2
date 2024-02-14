package uz.kun.repository;

import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.CommentLikeEntity;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity,Long> {
    Optional<CommentLikeEntity> findByCommentIdAndProfileId(Long id, Integer id1);
}
