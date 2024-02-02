package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.kun.dto.SmsHistoryDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.SmsHistoryService;
import uz.kun.utils.HttpRequestUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sms")
public class SmsHistoryController {

    @Autowired
    private SmsHistoryService smsHistoryService;
    @GetMapping("/adm/byPhone")
    public ResponseEntity<List<SmsHistoryDTO>> getByPhone(@RequestParam(name = "phone") String phone,
                                                          HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        return ResponseEntity.ok(smsHistoryService.getByPhone(phone));
    }

    @GetMapping("/adm/byDate")
    public ResponseEntity<?> getByDate(@RequestParam(name = "date") LocalDate localDate,
                                       HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        return ResponseEntity.ok(smsHistoryService.getByDate(localDate));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<?> pagination(@RequestParam Integer page,
                                        @RequestParam Integer size,
                                        HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        return ResponseEntity.ok(smsHistoryService.pagination(page,size));
    }
}
