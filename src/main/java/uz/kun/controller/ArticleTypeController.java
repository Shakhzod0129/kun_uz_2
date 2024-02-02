package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.ArticleTypeDTO;
import uz.kun.dto.JWTDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.ArticleTypeService;
import uz.kun.utils.HttpRequestUtil;
import uz.kun.utils.JWTUtil;

import java.util.List;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("/adm")
    public ResponseEntity<ArticleTypeDTO>  create(@RequestBody ArticleTypeDTO dto,
                                                  HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.create(dto));
    }

    @GetMapping("/adm/{id}")
    public ResponseEntity<ArticleTypeDTO> getById(@PathVariable Integer id,
                                                  HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.findById(id));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,
                                          @RequestBody ArticleTypeDTO dto,
                                          HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.updateById(id,dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id,
                                              HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.deleteById(id));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<ArticleTypeDTO>> pagination(@RequestParam Integer page,
                                                               @RequestParam Integer size,
                                                               HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.pagination(page,size));
    }

    @GetMapping("/byLang")
    public ResponseEntity<List<ArticleTypeDTO>> getByLanguage(@RequestParam String keyLang,
                                                              @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(articleTypeService.getByLang(keyLang));
    }
}
