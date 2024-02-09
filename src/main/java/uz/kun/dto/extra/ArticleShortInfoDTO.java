package uz.kun.dto.extra;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleShortInfoDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private Integer viewCount;
    private String imgId;
    private Integer regionId;
    private Integer categoryId;
    private Integer typeId;
    private List<Integer> typeIdList;
    private LocalDateTime publishDate;
}
