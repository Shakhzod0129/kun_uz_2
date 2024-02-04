package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.kun.dto.ArticleDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.ArticleService;
import uz.kun.utils.HttpRequestUtil;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/moderator")
    public ResponseEntity<ArticleDTO> create(@RequestBody ArticleDTO dto,
                                             HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(dto));

    }

}
