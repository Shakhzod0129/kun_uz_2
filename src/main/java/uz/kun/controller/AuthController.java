package uz.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.RegistrationDTO;
import uz.kun.service.AuthService;
import uz.kun.service.EmailHistoryService;
import uz.kun.service.SmsHistoryService;

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
    public ResponseEntity<ProfileDTO> login(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(authService.auth(dto));
    }

    @PostMapping("/registration")
    public ResponseEntity<Boolean> registration(@RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(emailHistoryService.registration(dto));
    }

    @GetMapping("/verification/email/{jwt}")
    public ResponseEntity<Boolean> emailVerification(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(emailHistoryService.emailVerification(jwt));
    }

    @PostMapping("/registrWithSms")
    public ResponseEntity<?> register(@RequestBody ProfileDTO dto){
        return ResponseEntity.ok(smsHistoryService.register(dto));
    }

    @PostMapping("/verification/sms")
    public ResponseEntity<?> verify(@RequestBody ProfileDTO dto){
        return ResponseEntity.ok(smsHistoryService.verify(dto));
    }



}
