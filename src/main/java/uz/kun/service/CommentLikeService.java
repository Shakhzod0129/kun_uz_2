package uz.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.dto.CommentLikeDTO;
import uz.kun.entity.*;
import uz.kun.enums.Status;
import uz.kun.repository.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentLikeService {

    @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ProfileService profileService;
    private static final int increment = 1;
    private static final int decrement = -1;


    public CommentLikeDTO create(CommentLikeDTO dto, Integer profileId) {

        CommentEntity commentEntity = commentService.get(dto.getCommentId());
        ProfileEntity profileEntity = profileService.get(profileId);
        Status newStatus = dto.getStatus();

        CommentLikeEntity entity = new CommentLikeEntity();

        Optional<CommentLikeEntity> optional = commentLikeRepository.findByCommentIdAndProfileId(commentEntity.getId(), profileEntity.getId());

        if (optional.isEmpty()) {
            entity.setCommentID(commentEntity.getId());
            entity.setProfileId(profileEntity.getId());
            entity.setCreatedDate(LocalDateTime.now());
            entity.setStatus(newStatus);

            commentLikeRepository.save(entity);
            updateCount(commentEntity, newStatus, increment);
        } else {
            CommentLikeEntity commentLikeEntity = optional.get();
            Status oldStatus = commentLikeEntity.getStatus();

            if (newStatus.equals(oldStatus)) {
                updateCount(commentEntity, oldStatus, decrement);

                commentLikeRepository.delete(commentLikeEntity);
            } else {
                commentLikeEntity.setStatus(newStatus);
                updateCount(commentEntity, oldStatus, decrement);
                updateCount(commentEntity, newStatus, increment);

                commentLikeRepository.save(commentLikeEntity);
            }

        }
        return dto;
    }

    private void updateCount(CommentEntity commentEntity, Status status, int delta) {

        if (status == Status.LIKE) {
            commentEntity.setLikeCount(commentEntity
                    .getLikeCount() + delta);
        } else if (status == Status.DISLIKE) {
            commentEntity.setDislikeCount(commentEntity
                    .getDislikeCount() + delta);
        }
        commentRepository.save(commentEntity);
    }

}
