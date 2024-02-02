package uz.kun.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicReference;
@Getter
@Setter
public class RegistrationDTO {
    private String name;
    private String surname;
    private String password;
    private String phone;
    private String email;
}
