package uz.kun.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.websocket.OnClose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import uz.kun.enums.ArticleStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

// id(uuid),title,description,content,shared_count,image_id,
//    region_id,category_id,moderator_id,publisher_id,status(Published,NotPublished)
//    created_date,published_date,visible,view_count
//    (Bitta article bir-nechta type da bo'lishi mumkun. Masalan Asosiy,Muharrir da.)
@Getter
@Setter
@Entity
@Table(name = "article")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(name = "title_uz")
    private String titleUz;
    @Column(name = "title_ru")
    private String titleRu;
    @Column(name = "title_en")
    private String titleEn;
    @Column(name = "description_uz")
    private String descriptionUz;
    @Column(name = "description_ru")
    private String descriptionRu;
    @Column(name = "description_en")
    private String descriptionEn;
    @Column(name = "content_uz")
    private String contentUz;
    @Column(name = "content_ru")
    private String contentRu;
    @Column(name = "content_en  ")
    private String contentEn;
    private Boolean visible;
    @Column(name = "shared_count")
    private Integer sharedCount;
    @Column(name = "view_count")
    private Integer viewCount;

    public ArticleEntity() {
        this.sharedCount = 0;
        this.viewCount = 0;
        this.visible = false;
    }

    @Column(name = "image_id")
    private String imageId;
    @ManyToOne
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private AttachEntity image;

    @Column(name = "region_id")
    private Integer regionid;
    @ManyToOne
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;


    @Column(name = "moderator_id")
    private Integer moderatorId;
    @ManyToOne
    @JoinColumn(name = "moderator_id", nullable = false, insertable = false, updatable = false)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private Integer publisherId;
    @ManyToOne
    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    private ProfileEntity publisher;

    @Enumerated(EnumType.STRING)
    private ArticleStatus status;
    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "category_id")
    private Integer categoryId;
    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "article")
    private List<ArticleAndItsTypeEntity> articleAndItsTypeEntityList;

    @OneToMany(mappedBy = "article")
    private List<ArticleAndTagNameEntity> articleAndTagNameEntityList;

}
