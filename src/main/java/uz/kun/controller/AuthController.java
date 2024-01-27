package uz.kun.controller;

import uz.kun.dto.AuthDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO dto){
        return ResponseEntity.ok(authService.auth(dto));
    }

}
