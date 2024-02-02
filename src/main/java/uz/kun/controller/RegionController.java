package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.JWTDTO;
import uz.kun.dto.RegionDTO;
import uz.kun.enums.AppLanguage;
import uz.kun.enums.ProfileRole;
import uz.kun.service.RegionService;
import uz.kun.utils.HttpRequestUtil;
import uz.kun.utils.JWTUtil;

import java.util.List;

@RestController
@RequestMapping("/region")
public class
RegionController {

    @Autowired
    private RegionService regionService;

    @PostMapping("/adm")
    public ResponseEntity<RegionDTO>  create(@RequestBody RegionDTO dto,
                                             HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.create(dto));
    }

    @GetMapping("/adm/{id}")
    public ResponseEntity<RegionDTO> getById(@PathVariable Integer id,
                                             HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.findById(id));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,
                                          @RequestBody RegionDTO dto,
                                          HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.updateById(id,dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id,
                                              HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.deleteById(id));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<RegionDTO>> pagination(@RequestParam Integer page,
                                                          @RequestParam Integer size,
                                                          HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.pagination(page,size));
    }

    @GetMapping("/byLang")
    public ResponseEntity<List<RegionDTO>> getByLanguage(@RequestParam(value = "language",defaultValue = "uz")
                                                             AppLanguage language){
        return ResponseEntity.ok(regionService.getByLang(language));
    }
}
