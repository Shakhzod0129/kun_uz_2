package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.kun.config.CustomUserDetails;
import uz.kun.dto.TagNameDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.TagNameService;
import uz.kun.utils.HttpRequestUtil;
import uz.kun.utils.SpringSecurityUtil;

@RestController
@RequestMapping("/tag")
public class TagNameController {

    @Autowired
    private TagNameService tagNameService;

    @PostMapping("/adm")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TagNameDTO> create(@RequestBody TagNameDTO dto){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(tagNameService.create(dto));
    }
}
