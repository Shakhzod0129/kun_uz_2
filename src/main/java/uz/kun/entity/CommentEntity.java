package uz.kun.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "article_id")
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id",insertable = false,updatable = false)
    private ArticleEntity article;

    @Column(name = "profile_id")
    private Integer profileID;
    @ManyToOne
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity author;

    @Column(name = "reply_id")
    private Long replyId;
    @ManyToOne
    @JoinColumn(name = "reply_id",insertable = false,updatable = false) // Bu maydon reply qilgan sharhni ID'sini saqlaydi
    private CommentEntity replyTo; // Bu maydon reply qilingan sharh obyektini saqlaydi

    @Column(name = "created_date")
    private LocalDateTime createdDate;
    // Qolgan xususiyatlar, getter va setterlar...

    @Column(name = "like_count")
    private Long likeCount=0l;
    @Column(name = "dislike_count")
    private Long dislikeCount=0l;
}