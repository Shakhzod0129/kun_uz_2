package uz.kun.dto.extra;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CreatedArticleDto {
    private String titleUz;
    private String titleRu;
    private String titleEn;
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
    private String contentUz;
    private String contentRu;
    private String contentEn;
    private String title;
    private String description;
    private String content;

    private Integer regionId;
    private Integer categoryId;
    private List<Integer> articleType; //
    private String photoId;
    private Integer moderatorID;
    private List<Long> tagName;
}
