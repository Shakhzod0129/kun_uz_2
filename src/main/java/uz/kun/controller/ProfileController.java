package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.kun.config.CustomUserDetails;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.ProfileFilterDto;
import uz.kun.enums.ProfileRole;
import uz.kun.service.ProfileService;
import uz.kun.utils.HttpRequestUtil;
import uz.kun.utils.SpringSecurityUtil;

import javax.swing.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;


    @PostMapping("/adm")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(profileService.create(dto));
    }

    @GetMapping("/adm/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProfileDTO> getById(@PathVariable Integer id){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(profileService.findById(id));
    }

    @PutMapping("/adm/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,
                                          @RequestBody ProfileDTO dto){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(profileService.updateById(id,dto));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_PUBLISHER','ROLE_USER')")
    public ResponseEntity<Boolean> updateDetail(@RequestBody ProfileDTO dto){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(profileService.updateDetail(currentUser.getId(),dto));
    }

    @DeleteMapping("/adm/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(profileService.deleteById(id));
    }

    @GetMapping("/adm/pagination")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageImpl<ProfileDTO>> pagination(@RequestParam Integer page,
                                                          @RequestParam Integer size){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(profileService.pagination(page,size));
    }

    @GetMapping("/adm/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestBody ProfileFilterDto dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(profileService.filter(dto,page,size));
    }





}
