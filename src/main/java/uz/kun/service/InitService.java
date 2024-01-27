package uz.kun.service;

import uz.kun.entity.ProfileEntity;
import uz.kun.enums.ProfileRole;
import uz.kun.enums.ProfileStatus;
import uz.kun.repository.ProfileRepository;
import uz.kun.utils.MDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InitService {

    @Autowired
    private ProfileRepository profileRepository;
    public void initAdmin() {
        String adminEmail = "admin@mail.ru";
        Optional<ProfileEntity> optional = profileRepository.findByEmail(adminEmail);
        if (optional.isPresent()) {
            return;
        }
        // create admin
        ProfileEntity admin = new ProfileEntity();
        admin.setName("Admin");
        admin.setSurname("Adminjon");
        admin.setPhone("7777");
        admin.setEmail(adminEmail);
        admin.setStatus(ProfileStatus.ACTIVE);
        admin.setRole(ProfileRole.ADMIN);
        admin.setPassword(MDUtil.encode("12345"));
        profileRepository.save(admin);
    }

}
