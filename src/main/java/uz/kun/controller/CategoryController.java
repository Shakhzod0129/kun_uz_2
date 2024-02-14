package uz.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.kun.config.CustomUserDetails;
import uz.kun.dto.CategoryDTO;
import uz.kun.service.CategoryService;
import uz.kun.utils.SpringSecurityUtil;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/adm")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @GetMapping("/adm/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Integer id){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PutMapping("/adm/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,
                                          @RequestBody CategoryDTO dto){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(categoryService.updateById(id,dto));
    }

    @DeleteMapping("/adm/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(categoryService.deleteById(id));
    }

    @GetMapping("/adm/pagination")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageImpl<CategoryDTO>> pagination(@RequestParam Integer page,
                                                               @RequestParam Integer size){
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(categoryService.pagination(page,size));
    }

    @GetMapping("/byLang")
    public ResponseEntity<List<CategoryDTO>> getByLanguage(@RequestParam String keyLang){
        return ResponseEntity.ok(categoryService.getByLang(keyLang));
    }
}
