package uz.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.CategoryDTO;
import uz.kun.dto.JWTDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.CategoryService;
import uz.kun.utils.JWTUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto,
                                              @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Integer id,
                                               @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,
                                          @RequestBody CategoryDTO dto,
                                          @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(categoryService.updateById(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id,
                                              @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(categoryService.deleteById(id));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<CategoryDTO>> pagination(@RequestParam Integer page,
                                                               @RequestParam Integer size,
                                                            @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(categoryService.pagination(page,size));
    }

    @GetMapping("/byLang")
    public ResponseEntity<List<CategoryDTO>> getByLanguage(@RequestParam String keyLang,
                                                           @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(categoryService.getByLang(keyLang));
    }
}
