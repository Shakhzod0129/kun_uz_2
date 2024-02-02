package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.JWTDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.ProfileFilterDto;
import uz.kun.enums.ProfileRole;
import uz.kun.service.ProfileService;
import uz.kun.utils.HttpRequestUtil;
import uz.kun.utils.JWTUtil;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;


    @PostMapping("/adm")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto,
                                             HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto));
    }

    @GetMapping("/adm/{id}")
    public ResponseEntity<ProfileDTO> getById(@PathVariable Integer id,
                                              HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.findById(id));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,
                                          @RequestBody ProfileDTO dto,
                                          HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.updateById(id,dto));
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateDetail(
                                          @RequestBody ProfileDTO dto,
                                          HttpServletRequest request){
        Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");

        if (id==null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(profileService.updateDetail(id,dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id,
                                              HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.deleteById(id));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<ProfileDTO>> pagination(@RequestParam Integer page,
                                                          @RequestParam Integer size,
                                                           HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.pagination(page,size));
    }

    @GetMapping("/adm/filter")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestBody ProfileFilterDto dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                       HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.filter(dto,page,size));
    }





}
