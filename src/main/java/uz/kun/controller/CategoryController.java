package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.CategoryDTO;
import uz.kun.dto.JWTDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.CategoryService;
import uz.kun.utils.HttpRequestUtil;
import uz.kun.utils.JWTUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/adm")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto,
                                              HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @GetMapping("/adm/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Integer id,
                                               HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,
                                          @RequestBody CategoryDTO dto,
                                          HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.updateById(id,dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id,
                                              HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.deleteById(id));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<CategoryDTO>> pagination(@RequestParam Integer page,
                                                               @RequestParam Integer size,
                                                            HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.pagination(page,size));
    }

    @GetMapping("/byLang")
    public ResponseEntity<List<CategoryDTO>> getByLanguage(@RequestParam String keyLang){
        return ResponseEntity.ok(categoryService.getByLang(keyLang));
    }
}
