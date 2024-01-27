package uz.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.dto.AuthDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.entity.ProfileEntity;
import uz.kun.exception.AppBadException;
import uz.kun.repository.ProfileRepository;
import uz.kun.utils.JWTUtil;
import uz.kun.utils.MDUtil;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    protected ProfileRepository profileRepository;

    public ProfileDTO auth(AuthDTO profile) { // login
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPassword(profile.getEmail(),
                MDUtil.encode(profile.getPassword()));

        if (optional.isEmpty()) {
            throw new AppBadException("Email or Password is wrong");
        }

        ProfileEntity entity = optional.get();

        ProfileDTO dto = new ProfileDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
        dto.setPhone(entity.getPhone());
        dto.setJwt(JWTUtil.encode(entity.getId(), (entity.getRole())));

        return dto;
    }

}

