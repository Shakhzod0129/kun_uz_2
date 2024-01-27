package uz.kun.controller;

import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.JWTDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.ProfileFilterDto;
import uz.kun.enums.AppLanguage;
import uz.kun.enums.ProfileRole;
import uz.kun.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import uz.kun.utils.JWTUtil;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;


    @PostMapping("")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto,
                                            @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(profileService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getById(@PathVariable Integer id,
                                             @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(profileService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,
                                          @RequestBody ProfileDTO dto,
                                          @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(profileService.updateById(id,dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateDetail(@PathVariable Integer id,
                                          @RequestBody ProfileDTO dto,
                                          @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getId().equals(id)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(profileService.updateDetail(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id,
                                              @RequestHeader(value = "Authorization") String jwt){

        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(profileService.deleteById(id));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<ProfileDTO>> pagination(@RequestParam Integer page,
                                                          @RequestParam Integer size,
                                                          @RequestHeader(value = "Authorization") String jwt){
        JWTDTO jwtdto= JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(profileService.pagination(page,size));
    }

    @GetMapping("/filter")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestBody ProfileFilterDto dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                       @RequestHeader (value="Authorization") String jwt){
        JWTDTO jwtdto=JWTUtil.decode(jwt);
        if(!jwtdto.getRole().equals(ProfileRole.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(profileService.filter(dto,page,size));
    }





}
