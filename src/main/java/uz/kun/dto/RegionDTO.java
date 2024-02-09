package uz.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDTO {
    private Integer id;
    @NotNull(message = "Order number required!!!")
    private Integer orderNumber;
    @Size(min=10,max = 200, message="NameUz must be between 10 and 200 characters")
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String name;
    private Boolean visible;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
