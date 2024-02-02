package uz.kun.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "email_send_histoy")
public class EmailSendHistoryEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "message", columnDefinition = "Text")
    private String message;
    private String email;
    @Column(name = "created_date")
    private LocalDateTime createdDate;


}
