package uz.kun.repository;

import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.CommentEntity;

public interface CommentRepository extends CrudRepository<CommentEntity, Long>   {
}
