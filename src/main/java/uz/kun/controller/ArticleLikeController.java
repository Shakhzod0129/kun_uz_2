package uz.kun.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.ArticleLikeDTO;
import uz.kun.service.ArticleLikeService;

@RestController
@RequestMapping("/likesAndDislikes")
public class ArticleLikeController {

    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("/create")
    public ResponseEntity<ArticleLikeDTO> create(@Valid @RequestBody ArticleLikeDTO dto){
        return ResponseEntity.ok(articleLikeService.create(dto));
    }
}
