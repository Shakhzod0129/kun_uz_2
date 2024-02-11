package uz.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import uz.kun.enums.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleLikeDTO {
    private Long id;
    private Status status;
    private String articleID;
    private Integer profileId;
    private LocalDateTime createdDate;
}
