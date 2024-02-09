package uz.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.dto.TagNameDTO;
import uz.kun.entity.TagNameEntity;
import uz.kun.exception.AppBadException;
import uz.kun.repository.TagNameRepository;

import java.time.LocalDateTime;

@Service
public class TagNameService {

    @Autowired
    private TagNameRepository tagNameRepository;


    public TagNameDTO create(TagNameDTO dto) {

        TagNameEntity entity=new TagNameEntity();

        entity.setTagName(dto.getName());
        entity.setCreatedDate(LocalDateTime.now());

        tagNameRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setName(entity.getTagName());

        return dto;
    }


    public TagNameEntity get(Long tagId){
        return tagNameRepository.findById(tagId).orElseThrow(() -> new AppBadException("Tag not found"));
    }
}
