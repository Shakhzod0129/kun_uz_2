package uz.kun.repository;

import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.ArticleEntity;

public interface ArticleRepository extends CrudRepository<ArticleEntity,Integer> {


}
