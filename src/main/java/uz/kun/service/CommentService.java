package uz.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.dto.CommentDTO;
import uz.kun.entity.ArticleEntity;
import uz.kun.entity.CommentEntity;
import uz.kun.entity.ProfileEntity;
import uz.kun.repository.CommentRepository;

import java.time.LocalDateTime;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ProfileService profileService;

    public CommentDTO create(CommentDTO dto, Integer profileId){
        ArticleEntity articleEntity = articleService.get(dto.getArticleID());
        ProfileEntity profileEntity = profileService.get(profileId);

        CommentEntity commentEntity=new CommentEntity();

        commentEntity.setContent(dto.getContent());
        commentEntity.setArticleId(articleEntity.getId());
        commentEntity.setProfileID(profileEntity.getId());
        commentEntity.setReplyId(dto.getReplyId());
        commentEntity.setCreatedDate(LocalDateTime.now());

        commentRepository.save(commentEntity);

        dto.setId(commentEntity.getId());
        dto.setContent(commentEntity.getContent());
        dto.setArticleID(commentEntity.getArticleId());
        dto.setProfileId(commentEntity.getProfileID());
        dto.setCreatedDate(commentEntity.getCreatedDate());

        return dto;

    }
}
