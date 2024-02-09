package uz.kun.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.kun.dto.TagNameDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.TagNameService;
import uz.kun.utils.HttpRequestUtil;

@RestController
@RequestMapping("/tag")
public class TagNameController {

    @Autowired
    private TagNameService tagNameService;

    @PostMapping("/adm")
    public ResponseEntity<TagNameDTO> create(@RequestBody TagNameDTO dto, HttpServletRequest request) {
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        return ResponseEntity.ok(tagNameService.create(dto));
    }
}
