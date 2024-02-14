package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.kun.config.CustomUserDetails;
import uz.kun.dto.EmailHistoryDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.EmailHistoryService;
import uz.kun.utils.HttpRequestUtil;
import uz.kun.utils.SpringSecurityUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryService emailHistoryService;

    @GetMapping("/adm/byEmail")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EmailHistoryDTO>> getByEmail(@RequestParam String email){

        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(emailHistoryService.getByEmail(email));
    }

    @GetMapping("/adm/byDate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getByDate(@RequestParam(name = "date") LocalDate localDate,
                                       HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);

        return ResponseEntity.ok(emailHistoryService.getByDate(localDate));
    }

    @GetMapping("/adm/pagination")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> pagination(@RequestParam Integer page,
                                        @RequestParam Integer size){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(emailHistoryService.pagination(page,size));
    }
}
