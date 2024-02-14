package uz.kun.service;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.dto.AuthDTO;
import uz.kun.dto.JWTDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.RegistrationDTO;
import uz.kun.entity.ProfileEntity;
import uz.kun.enums.ProfileRole;
import uz.kun.enums.ProfileStatus;
import uz.kun.exception.AppBadException;
import uz.kun.repository.ProfileRepository;
import uz.kun.utils.JWTUtil;
import uz.kun.utils.MDUtil;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO auth(AuthDTO auth){
        Optional<ProfileEntity> optional =
                profileRepository.findByEmailAndPassword(auth.getEmail(), MDUtil.encode(auth.getPassword()));
        if (optional.isEmpty()) {
            throw new AppBadException("Email or Password is wrong");
        }
        if (!optional.get().getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppBadException("Profile not active");
        }

        ProfileEntity entity = optional.get();

        ProfileDTO dto = new ProfileDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
        dto.setJwt(JWTUtil.encode(entity.getEmail(),entity.getRole()));

        return dto;
    }




}

