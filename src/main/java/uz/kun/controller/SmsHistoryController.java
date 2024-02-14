package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.kun.config.CustomUserDetails;
import uz.kun.dto.SmsHistoryDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.SmsHistoryService;
import uz.kun.utils.HttpRequestUtil;
import uz.kun.utils.SpringSecurityUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sms")
public class SmsHistoryController {

    @Autowired
    private SmsHistoryService smsHistoryService;
    @GetMapping("/adm/byPhone")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<SmsHistoryDTO>> getByPhone(@RequestParam(name = "phone") String phone){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(smsHistoryService.getByPhone(phone));
    }

    @GetMapping("/adm/byDate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getByDate(@RequestParam(name = "date") LocalDate localDate){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(smsHistoryService.getByDate(localDate));
    }

    @GetMapping("/adm/pagination")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> pagination(@RequestParam Integer page,
                                        @RequestParam Integer size){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(smsHistoryService.pagination(page,size));
    }
}
