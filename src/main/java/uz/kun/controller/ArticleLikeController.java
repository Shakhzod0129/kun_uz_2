package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.kun.config.CustomUserDetails;
import uz.kun.dto.ArticleLikeDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.ArticleLikeService;
import uz.kun.utils.HttpRequestUtil;
import uz.kun.utils.SpringSecurityUtil;

@RestController
@RequestMapping("/likesAndDislikes")
public class ArticleLikeController {

    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("/any/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_PUBLISHER','ROLE_USER')")
    public ResponseEntity<ArticleLikeDTO> create(@Valid @RequestBody ArticleLikeDTO dto){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(articleLikeService.create(dto,currentUser.getId()));
    }
}
