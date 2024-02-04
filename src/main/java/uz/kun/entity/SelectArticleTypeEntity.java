package uz.kun.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionType;

@Getter
@Setter
@Entity
@Table(name = "select_article_type")
public class SelectArticleTypeEntity extends BaseEntity{
    @Column(name = "article_id")
    private Integer articleId;
    @Column(name = "article_type_id")
    private Integer articleTypeId;
}
