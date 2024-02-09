package uz.kun.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.AuthDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.RegistrationDTO;
import uz.kun.service.AuthService;
import uz.kun.service.EmailHistoryService;
import uz.kun.service.SmsHistoryService;
@Slf4j
@Tag(name = "Authorization Api list", description = "Api list for Authorization")

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private SmsHistoryService smsHistoryService;

    @Autowired
    private EmailHistoryService emailHistoryService;

    @PostMapping("/login")
    @Operation( summary = "Api for login", description = "this api used for authorization")
    public ResponseEntity<ProfileDTO> login(@Valid @RequestBody AuthDTO auth) {
        log.trace("login trace");
        log.debug("login debug");
        log.info("Login {} ", auth.getEmail());
        log.warn("Login {} ", auth.getEmail());
        log.error("Login {} ", auth.getEmail());

        return ResponseEntity.ok(authService.auth(auth));
    }

    @PostMapping("/registration")
    public ResponseEntity<Boolean> registration(@Valid @RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(emailHistoryService.registration(dto));
    }

    @GetMapping("/verification/email/{jwt}")
    public ResponseEntity<Boolean> emailVerification(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(emailHistoryService.emailVerification(jwt));
    }

    @PostMapping("/registrWithSms")
    public ResponseEntity<?> register(@Valid @RequestBody ProfileDTO dto){
        return ResponseEntity.ok(smsHistoryService.register(dto));
    }

    @PostMapping("/verification/sms")
    public ResponseEntity<?> verify(@Valid @RequestBody ProfileDTO dto){
        return ResponseEntity.ok(smsHistoryService.verify(dto));
    }



}
