package uz.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.dto.ArticleDTO;
import uz.kun.entity.ArticleEntity;
import uz.kun.enums.ArticleStatus;
import uz.kun.exception.AppBadException;
import uz.kun.repository.ArticleRepository;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
//(title,description,content,image_id, region_id,category_id, articleType(array))
    public ArticleDTO create(ArticleDTO dto) {
        if(dto.getTitle()==null){
            throw new AppBadException("Title is null");
        }
        if(dto.getDescription()==null){
            throw new AppBadException("Description is null");
        }
        if(dto.getContent()==null){
            throw new AppBadException("Content is null");
        }
//        if(dto.getImageId()==null){
//            throw new AppBadException("Title is null");
//        }
        if(dto.getRegionId()==null){
            throw new AppBadException("Region_id is null");
        }
        if(dto.getCategoryId()==null){
            throw new AppBadException("Category_id is null");
        }
        if(dto.getArticleTypeId()==null){
            throw new AppBadException("Article_type_id is null");
        }

        ArticleEntity entity=new ArticleEntity();

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
//        entity.setRegionId(dto.getRegionId());
//        entity.setCategoryId(dto.getCategoryId());
//        entity.setArticleTypeId(dto.getArticleTypeId());
//        entity.setModeratorId(dto.getId());
        entity.setStatus(ArticleStatus.NO_PUBLISHED);

        articleRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }
}
