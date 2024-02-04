package uz.kun.dto;

import lombok.Getter;
import lombok.Setter;
import uz.kun.enums.ArticleStatus;

import java.time.LocalDateTime;

// id(uuid),title,description,content,shared_count,image_id,
//    region_id,category_id,moderator_id,publisher_id,status(Published,NotPublished)
//    created_date,published_date,visible,view_count
//    (Bitta article bir-nechta type da bo'lishi mumkun. Masalan Asosiy,Muharrir da.)
@Getter
@Setter
public class ArticleDTO {
    private Integer id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private Integer viewCount;
    private Integer articleTypeId;
    private Integer imageId;
    private Integer regionId;
    private Integer categoryId;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;

}
