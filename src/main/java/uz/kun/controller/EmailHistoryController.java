package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.EmailHistoryDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.SmsHistoryDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.EmailHistoryService;
import uz.kun.utils.HttpRequestUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryService emailHistoryService;

    @GetMapping("/adm/byEmail")
    public ResponseEntity<List<EmailHistoryDTO>> getByEmail(@RequestParam String email,
                                                            HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        return ResponseEntity.ok(emailHistoryService.getByEmail(email));
    }

    @GetMapping("/adm/byDate")
    public ResponseEntity<?> getByDate(@RequestParam(name = "date") LocalDate localDate,
                                       HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        return ResponseEntity.ok(emailHistoryService.getByDate(localDate));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<?> pagination(@RequestParam Integer page,
                                        @RequestParam Integer size,
                                        HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        return ResponseEntity.ok(emailHistoryService.pagination(page,size));
    }
}
