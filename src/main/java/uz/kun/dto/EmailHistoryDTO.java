package uz.kun.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class EmailHistoryDTO {
    private Integer id;
    private String message;
    private String email;
    private LocalDateTime createdDate;
}
