package uz.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import uz.kun.enums.ProfileRole;
import uz.kun.enums.ProfileStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileFilterDto {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private ProfileStatus status;
    private ProfileRole role;
    private Boolean visible;
    private LocalDate fromDate;
    private LocalDate toDate;

}
