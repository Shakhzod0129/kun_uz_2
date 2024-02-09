package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.ArticleDTO;
import uz.kun.dto.extra.ArticleFullInfoDTO;
import uz.kun.dto.extra.ArticleIdDTO;
import uz.kun.dto.extra.ArticleShortInfoDTO;
import uz.kun.dto.extra.CreatedArticleDto;
import uz.kun.enums.AppLanguage;
import uz.kun.enums.ArticleStatus;
import uz.kun.enums.ProfileRole;
import uz.kun.service.ArticleService;
import uz.kun.utils.HttpRequestUtil;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/moderator")
    public ResponseEntity<ArticleDTO> create(@RequestBody CreatedArticleDto dto,
                                             HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(dto,profileId));

    }

    @PutMapping("/moderator/{articleId}")
    public ResponseEntity<ArticleDTO> update(@PathVariable String articleId,
                                             @RequestBody ArticleDTO dto,
                                             HttpServletRequest request){
        if (articleId  == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);

        return ResponseEntity.ok(articleService.update(articleId,dto,profileId));

    }

    @DeleteMapping("/moderator/{articleId}")
    public ResponseEntity<Boolean> delete(@PathVariable String articleId,
                                          HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.delete(articleId));
    }

    @PutMapping("/moderator/changeStatus")
    public ResponseEntity<Boolean> changeStatus(@RequestParam String id,
                                          @RequestParam ArticleStatus status,
                                          HttpServletRequest request){
        if (id  == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.PUBLISHER);

        return ResponseEntity.ok(articleService.changeStatus(id,status));

    }

    @GetMapping("/last5ByType")
    public ResponseEntity<List<ArticleShortInfoDTO>> last5ByType(@RequestParam Integer typeId){
        return ResponseEntity.ok(articleService.last5ByType(typeId));
    }

    @GetMapping("/last3ByType")
    public ResponseEntity<List<ArticleShortInfoDTO>> last3ByType(@RequestParam Integer typeId){
        return ResponseEntity.ok(articleService.last3ByType(typeId));
    }

    @GetMapping("/last8ByType")
    public ResponseEntity<List<ArticleShortInfoDTO>> last8ByType(@RequestBody  ArticleIdDTO dto){
        return ResponseEntity.ok(articleService.last8ByType(dto));
    }


    @GetMapping("/last4ByType")
    public ResponseEntity<List<ArticleShortInfoDTO>> getLast4ByType(@RequestParam Integer typeId,
                                                                 @RequestParam String articleId){
        return ResponseEntity.ok(articleService.last4ByType(typeId,articleId));
    }

    @GetMapping("/mostRead4Article")
    public ResponseEntity<List<ArticleShortInfoDTO>> getMostRead4Article(){
        return ResponseEntity.ok(articleService.getMostRead4Article());
    }

    @GetMapping("/last4ByTagId")
    public ResponseEntity<List<ArticleShortInfoDTO>> last4ByTagId(@RequestParam Long tagId){
        return ResponseEntity.ok(articleService.last4ByTagId(tagId));
    }

    @GetMapping("/getByTypeIdAndRegionId")
    public ResponseEntity<List<ArticleShortInfoDTO>> getByTypeIdAndRegionId(@RequestParam Integer typeId,
                                                                            @RequestParam Integer regionId){
        return ResponseEntity.ok(articleService.getByTypeIdAndRegionId(typeId, regionId));
    }

    @GetMapping("/paginationByRegionId")
    public ResponseEntity<PageImpl<ArticleShortInfoDTO>> paginationByRegionId(@RequestParam Integer page,
                                                                              @RequestParam Integer size,
                                                                              @RequestParam Integer regionId){
        return ResponseEntity.ok(articleService.paginationByRegionId2(page, size, regionId));
    }

    @GetMapping("/last5ByCategoryId")
    public ResponseEntity<List<ArticleShortInfoDTO>> last5ByCategoryId(@RequestParam Integer categoryID){
        return ResponseEntity.ok(articleService.last5ByCategoryId(categoryID));
    }

    @GetMapping("/paginationByCategoryId")
    public ResponseEntity<PageImpl<ArticleShortInfoDTO>> paginationByCategoryId(@RequestParam Integer page,
                                                                              @RequestParam Integer size,
                                                                              @RequestParam Integer categoryId){
        return ResponseEntity.ok(articleService.paginationByCategoryId(page, size, categoryId));
    }

    @PutMapping("/increaseSharedCountByArticleId")
    public ResponseEntity<ArticleShortInfoDTO> increaseSharedCountByArticleId(@RequestParam String  articleId){
        return ResponseEntity.ok(articleService.increaseSharedCountByArticleId(articleId));
    }

    @PutMapping("/increaseViewCountByArticleId")
    public ResponseEntity<ArticleShortInfoDTO> increaseViewCountByArticleId(@RequestParam String  articleId){
        return ResponseEntity.ok(articleService.increaseViewCountByArticleId(articleId));
    }

    @GetMapping("/byLang")
    public ResponseEntity<ArticleFullInfoDTO> getByLanguage(@RequestParam(value = "language",defaultValue = "uz")
                                                         AppLanguage language,
                                                            @RequestParam String articleId){
        return ResponseEntity.ok(articleService.getByLang(language,articleId));
    }












}

