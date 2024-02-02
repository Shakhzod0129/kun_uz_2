package uz.kun.entity;

import uz.kun.enums.ProfileRole;
import uz.kun.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false,unique = true)
    private String phone;
    @Column(nullable = false)
    private String password;
    @Column (name = "sms_code")
    private String smsCode;
    @Column(name = "sms_code_time")
    private LocalDateTime smsCodeTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ProfileRole role;
    private String jwt;

}
