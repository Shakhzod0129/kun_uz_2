package uz.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsHistoryDTO {
    private Integer id;
    private String message;
    private String userPhone;
    private LocalDateTime createdDate;
}
