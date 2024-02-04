package uz.kun.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "article")
public class ArticleEntity extends BaseEntity {

    private String title;
    private String description;
    private String content;
    @Column(name = "shared_count")
    private Integer sharedCount;
    @Column(name = "view_count")
    private Integer viewCount;
    @ManyToOne
    @JoinColumn(name = "article_type_id")
    private SelectArticleTypeEntity articleTypeId;

    @Column(name = "image_id")
    private Integer imageId;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private RegionEntity region;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @ManyToOne
    @JoinColumn(name = "moderator_id",nullable = false)
    private ProfileEntity moderator;
    @ManyToOne
    @JoinColumn(name = "publisher_id",nullable = false)
    private ProfileEntity publisher;

    private ArticleStatus status;
    @Column(name = "published_date")
    private LocalDateTime publishedDate;


}
