package uz.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import uz.kun.dto.extra.ArticleShortInfoDTO;
import uz.kun.dto.extra.ProfileShortInfoDto;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private Long id;
    private String content;
    private String articleID;
    private Integer profileId;
    private Long replyId;
    private LocalDateTime createdDate;
    private ProfileShortInfoDto profile;
    private ArticleShortInfoDTO article;

}
