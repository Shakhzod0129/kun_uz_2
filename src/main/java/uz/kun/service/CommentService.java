package uz.kun.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.kun.dto.CommentDTO;
import uz.kun.dto.extra.ArticleShortInfoDTO;
import uz.kun.dto.extra.ProfileShortInfoDto;
import uz.kun.entity.ArticleEntity;
import uz.kun.entity.CommentEntity;
import uz.kun.entity.ProfileEntity;
import uz.kun.exception.AppBadException;
import uz.kun.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ProfileService profileService;

    public CommentDTO create(CommentDTO dto, Integer id) {
        ArticleEntity articleEntity = articleService.get(dto.getArticleID());
        ProfileEntity profileEntity = profileService.get(id);

        CommentEntity commentEntity = new CommentEntity();

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

    public boolean update(CommentDTO dto, Integer id) {
        CommentEntity commentEntity = get(dto.getId());

        if (!Objects.equals(commentEntity.getProfileID(), id) ||
                !Objects.equals(commentEntity.getArticleId(), dto.getArticleID())) {
            throw new AppBadException("You are not authorized to update this comment");
        }

        commentEntity.setContent(dto.getContent());
        commentRepository.save(commentEntity);

        return true;
    }

    public boolean delete(Long commentId) {
        CommentEntity commentEntity = get(commentId);
        commentRepository.deleteById(commentEntity.getId());
        return true;
    }

    public List<CommentDTO> getAll(String articleId){
        ArticleEntity entity = articleService.get(articleId);
        List<CommentEntity> list = commentRepository.findByArticleId(entity.getId());

        List<CommentDTO> dtoList=new LinkedList<>();

        for (CommentEntity commentEntity : list) {
            dtoList.add(toDto(commentEntity));
        }

        return dtoList;
    }

    public CommentDTO toDto(CommentEntity entity){
        CommentDTO dto=new CommentDTO();
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setContent(entity.getContent());
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileID());
        dto.setArticleID(entity.getArticleId());

        return dto;
    }


    public CommentEntity get(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new AppBadException("Comment not found with this id : " + commentId));
    }

    public PageImpl<CommentDTO> pagination(Integer page, Integer size) {

        Pageable pageable= PageRequest.of(page-1,size);

        Page<CommentEntity> all = commentRepository.findAll(pageable);

        List<CommentEntity> content = all.getContent();
        long totalElements = all.getTotalElements();

        List<CommentDTO> dtoList=new LinkedList<>();
        for (CommentEntity commentEntity : content) {
            ProfileEntity profileEntity = profileService.get(commentEntity.getProfileID());
            ProfileShortInfoDto profileShortInfoDto=new ProfileShortInfoDto();
            profileShortInfoDto.setId(profileEntity.getId());
            profileShortInfoDto.setName(profileEntity.getName());
            profileShortInfoDto.setSurname(profileEntity.getSurname());
            CommentDTO dto=new CommentDTO();
            ArticleEntity articleEntity = articleService.get(commentEntity.getArticleId());
            ArticleShortInfoDTO articleShortInfoDTO=new ArticleShortInfoDTO();
            articleShortInfoDTO.setId(articleEntity.getId());
            articleShortInfoDTO.setTitle(articleEntity.getTitleUz());

            dto.setArticle(articleShortInfoDTO);
            dto.setProfile(profileShortInfoDto);
            dto.setCreatedDate(commentEntity.getCreatedDate());
            dto.setContent(commentEntity.getContent());
            dto.setId(commentEntity.getId());

            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList,pageable,totalElements);


    }
}
