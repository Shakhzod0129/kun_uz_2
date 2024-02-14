package uz.kun.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.kun.config.CustomUserDetails;
import uz.kun.dto.CommentDTO;
import uz.kun.service.CommentService;
import uz.kun.utils.SpringSecurityUtil;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_PUBLISHER','ROLE_USER')")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO dto) {
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(commentService.create(dto,currentUser.getId()));
    }

    @PutMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_PUBLISHER','ROLE_USER')")
    public ResponseEntity<Boolean> update(@Valid @RequestBody CommentDTO dto){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(commentService.update(dto,currentUser.getId()));
    }

    @DeleteMapping("/adm/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(commentService.delete(id));
    }

    @GetMapping("/adm")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CommentDTO>> getAll(@RequestParam String articleId){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(commentService.getAll(articleId));

    }

    @GetMapping("adm/pagination")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageImpl<CommentDTO>> pagination(Integer page,Integer size){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(commentService.pagination(page,size));
    }


}
