package uz.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.kun.config.CustomUserDetails;
import uz.kun.dto.ArticleTypeDTO;
import uz.kun.service.ArticleTypeService;
import uz.kun.utils.SpringSecurityUtil;

import java.util.List;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("/adm")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ArticleTypeDTO>  create(@RequestBody ArticleTypeDTO dto){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(articleTypeService.create(dto));
    }

    @GetMapping("/adm/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ArticleTypeDTO> getById(@PathVariable Integer id){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(articleTypeService.findById(id));
    }

    @PutMapping("/adm/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,
                                          @RequestBody ArticleTypeDTO dto){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(articleTypeService.updateById(id,dto));
    }

    @DeleteMapping("/adm/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(articleTypeService.deleteById(id));
    }

    @GetMapping("/adm/pagination")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageImpl<ArticleTypeDTO>> pagination(@RequestParam Integer page,
                                                               @RequestParam Integer size){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(articleTypeService.pagination(page,size));
    }

    @GetMapping("/adm/byLang")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ArticleTypeDTO>> getByLanguage(@RequestParam String keyLang){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(articleTypeService.getByLang(keyLang));
    }
}
