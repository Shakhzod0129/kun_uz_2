package uz.kun.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.kun.enums.ProfileRole;


@Getter
@Setter
@AllArgsConstructor
public class JWTDTO {
    private Integer id;
    private ProfileRole role;

    public JWTDTO(Integer id) {
        this.id=id;
    }
}
