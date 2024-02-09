package uz.kun.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "article_tags")
public class ArticleAndTagNameEntity extends BaseEntity {

    @Column(name = "article_id")
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id",insertable = false,updatable = false)
    private ArticleEntity article;

    @Column(name = "tags_id")
    private Long tagsId;
    @ManyToOne
    @JoinColumn(name = "tags_id",insertable = false,updatable = false)
    private TagNameEntity types;
}
