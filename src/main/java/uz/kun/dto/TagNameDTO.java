package uz.kun.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TagNameDTO {

    private Long id;
    private String name;
    private LocalDateTime createdDate;

}
