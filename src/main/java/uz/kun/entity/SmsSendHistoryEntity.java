package uz.kun.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sms_send_histoy")
public class SmsSendHistoryEntity extends BaseEntity {
    private String message;
    @Column(name = "user_phone")
    private String userPhone;
}
