package uz.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.ArticleTypeDTO;
import uz.kun.dto.JWTDTO;
import uz.kun.dto.RegionDTO;
import uz.kun.entity.RegionEntity;
import uz.kun.enums.AppLanguage;
import uz.kun.enums.ProfileRole;
import uz.kun.service.RegionService;
import uz.kun.utils.JWTUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @PostMapping("")
    public ResponseEntity<RegionDTO>  create(@RequestBody RegionDTO dto,
                                             @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(regionService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> getById(@PathVariable Integer id,
                                             @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(regionService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,
                                          @RequestBody RegionDTO dto,
                                          @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(regionService.updateById(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id,
                                              @RequestHeader(value = "Authorization") String jwt){

        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(regionService.deleteById(id));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<RegionDTO>> pagination(@RequestParam Integer page,
                                                          @RequestParam Integer size,
                                                          @RequestHeader(value = "Authorization") String jwt){

        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(regionService.pagination(page,size));
    }

    @GetMapping("/byLang")
    public ResponseEntity<List<RegionDTO>> getByLanguage(@RequestParam(value = "language",defaultValue = "uz") AppLanguage language){
        return ResponseEntity.ok(regionService.getByLang(language));
    }
}
