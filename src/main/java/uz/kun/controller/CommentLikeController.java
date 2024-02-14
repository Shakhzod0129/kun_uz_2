package uz.kun.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.kun.config.CustomUserDetails;
import uz.kun.dto.CommentLikeDTO;
import uz.kun.service.CommentLikeService;
import uz.kun.utils.SpringSecurityUtil;

@RestController
@RequestMapping("/commentLike")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_PUBLISHER','ROLE_USER')")
    public ResponseEntity<CommentLikeDTO> create(@Valid @RequestBody CommentLikeDTO dto) {
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(commentLikeService.create(dto,currentUser.getId()));
    }
}
