package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.CommentDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.CommentService;
import uz.kun.utils.HttpRequestUtil;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO dto
                                                    ) {
        return ResponseEntity.ok(commentService.create(dto, dto.getProfileId()));
    }
//    @PutMapping("/update")
//    public ResponseEntity<Boolean> updateDetail(
//            @RequestBody ProfileDTO dto,
//            HttpServletRequest request){
//        Integer id = (Integer) request.getAttribute("id");
//        ProfileRole role = (ProfileRole) request.getAttribute("role");
//
//        if (id==null) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//        return ResponseEntity.ok(profileService.updateDetail(id,dto));
//    }

}
