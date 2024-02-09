package uz.kun.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicReference;
@Getter
@Setter
public class RegistrationDTO {
    private String name;
    private String surname;
    private String password;
    @NotNull(message = "Order number required!!!")
    private String phone;
    @Email(message = "Email should be valid", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
}
