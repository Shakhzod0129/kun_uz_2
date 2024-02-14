package uz.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.dto.ArticleLikeDTO;
import uz.kun.entity.ArticleEntity;
import uz.kun.entity.ArticleLikeEntity;
import uz.kun.entity.ProfileEntity;
import uz.kun.enums.Status;
import uz.kun.repository.ArticleLikeRepository;
import uz.kun.repository.ArticleRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleLikeService {


    @Autowired
    private ArticleLikeRepository articleLikeRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleRepository articleRepository;
    private static final int increment = 1;
    private static final int decrement = -1;


    public ArticleLikeDTO create(ArticleLikeDTO dto, Integer profileId) {

        ArticleEntity articleEntity = articleService.get(dto.getArticleID());
        ProfileEntity profileEntity = profileService.get(profileId);
        Status newStatus = dto.getStatus();

        ArticleLikeEntity entity = new ArticleLikeEntity();

        Optional<ArticleLikeEntity> optional = articleLikeRepository.findByArticleIdAndProfileId(articleEntity.getId(), profileEntity.getId());

        if (optional.isEmpty()) {
            entity.setArticleId(articleEntity.getId());
            entity.setProfileId(profileEntity.getId());
            entity.setCreatedDate(LocalDateTime.now());
            entity.setStatus(newStatus);

            articleLikeRepository.save(entity);
            updateCount(articleEntity, newStatus, increment);
        } else {
            ArticleLikeEntity articleLikeEntity = optional.get();
            Status oldStatus = articleLikeEntity.getStatus();

            if (newStatus.equals(oldStatus)) {
                updateCount(articleEntity, oldStatus, decrement);

                articleLikeRepository.delete(articleLikeEntity);
            } else {
                articleLikeEntity.setStatus(newStatus);
                updateCount(articleEntity, oldStatus, decrement);
                updateCount(articleEntity, newStatus, increment);

                articleLikeRepository.save(articleLikeEntity);
            }

        }
        return dto;
    }
    private void updateCount(ArticleEntity articleEntity, Status status, int delta) {

        if (status == Status.LIKE) {
            articleEntity.setLikeCount(articleEntity.getLikeCount() + delta);
        } else if (status == Status.DISLIKE) {
            articleEntity.setDislikeCount(articleEntity.getDislikeCount() + delta);
        }
        articleRepository.save(articleEntity);
    }


}
