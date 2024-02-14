package uz.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import uz.kun.enums.Status;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentLikeDTO {
    private Long id;
    private Status status;
    private Long commentId;
    private Integer profileId;
    private LocalDateTime createdDate;
}
