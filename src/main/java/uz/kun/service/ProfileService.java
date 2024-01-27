package uz.kun.service;

import org.springframework.data.domain.*;
import uz.kun.dto.PaginationResultDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.ProfileFilterDto;
import uz.kun.dto.RegionDTO;
import uz.kun.entity.ProfileEntity;
import uz.kun.entity.RegionEntity;
import uz.kun.enums.AppLanguage;
import uz.kun.enums.ProfileStatus;
import uz.kun.exception.AppBadException;
import uz.kun.repository.ProfileFilterRepository;
import uz.kun.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.utils.MDUtil;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService implements CrudService<ProfileDTO, Integer> {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileFilterRepository profileFilterRepository;

    @Override
    public ProfileDTO create(ProfileDTO dto) {

        if(dto.getName()==null||dto.getSurname()==null||dto.getPassword()==null||
                dto.getPhone()==null||dto.getEmail()==null||dto.getRole()==null){
            throw new AppBadException("required space should not empty!!!");
        }

        ProfileEntity entity=new ProfileEntity();

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MDUtil.encode(dto.getPassword()));
        entity.setRole(dto.getRole());
        entity.setVisible(true);
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());

        profileRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;

    }

    @Override
    public ProfileDTO findById(Integer id) {
        return toDTO(get(id));
    }

    @Override
    public Boolean updateById(Integer id, ProfileDTO dto) {

        ProfileEntity entity = get(id);
        if(dto.getName()==null||dto.getSurname()==null||dto.getPassword()==null||
                dto.getPhone()==null||dto.getEmail()==null||dto.getRole()==null){
            throw new AppBadException("required space should not empty!!!");
        }

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MDUtil.encode(dto.getPassword()));
        entity.setRole(dto.getRole());
        entity.setVisible(true);
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setUpdatedDate(LocalDateTime.now());

        profileRepository.save(entity);
        return true;
    }

    public Boolean updateDetail(Integer id,ProfileDTO dto) {
        ProfileEntity entity = get(id);
//        if(dto.getName()==null||dto.getSurname()==null||dto.getPassword()==null||
//                dto.getPhone()==null||dto.getEmail()==null||dto.getRole()==null){
//            throw new AppBadException("required space should not empty!!!");
//        }

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MDUtil.encode(dto.getPassword()));
        entity.setUpdatedDate(LocalDateTime.now());

        profileRepository.save(entity);
        return true;
    }


    @Override
    public Boolean deleteById(Integer id) {
        ProfileEntity entity = get(id);
        profileRepository.delete(entity.getId());
        return true;
    }

    public PageImpl<ProfileDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ProfileEntity> entityPage = profileRepository.findAllByVisible(pageable, true);

        List<ProfileEntity> content = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();

        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : entityPage) {
            dtoList.add(toDTO(entity));
        }

        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    public PageImpl<ProfileDTO> filter(ProfileFilterDto dto, Integer page, Integer size) {

        Sort sort=Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable=PageRequest.of(page,size,sort);

        PaginationResultDTO<ProfileEntity> pages = profileFilterRepository.filter(dto, page, size);

        List<ProfileDTO> dtoList=new LinkedList<>();

        for (ProfileEntity entity : pages.getList()) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList,pageable,pages.getTotalSize());

    }


    public ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setJwt(entity.getJwt());

        return dto;
    }

    public ProfileEntity get(Integer id){
        return profileRepository.findById(id).orElseThrow(() -> new AppBadException("Profile not found"));
    }



}
