package uz.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.ArticleTypeDTO;
import uz.kun.dto.JWTDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.ArticleTypeService;
import uz.kun.utils.JWTUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("")
    public ResponseEntity<ArticleTypeDTO>  create(@RequestBody ArticleTypeDTO dto,
                                                  @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(articleTypeService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleTypeDTO> getById(@PathVariable Integer id,
                                                  @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(articleTypeService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,
                                          @RequestBody ArticleTypeDTO dto,
                                          @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(articleTypeService.updateById(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id,
                                              @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(articleTypeService.deleteById(id));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<ArticleTypeDTO>> pagination(@RequestParam Integer page,
                                                               @RequestParam Integer size,
                                                               @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
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
