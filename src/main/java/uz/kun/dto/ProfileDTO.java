package uz.kun.dto;


import jakarta.validation.constraints.Email;
import uz.kun.enums.ProfileRole;
import uz.kun.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    private String name;
    private String surname;

    private String email;
    private String password;
    private String phone;
    private ProfileStatus status;
    private ProfileRole role;
    private String  smsCode;
    private LocalDateTime smsTimeOfSending;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean visible;

    private String jwt;
}
