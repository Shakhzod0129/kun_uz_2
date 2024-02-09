package uz.kun.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.SmsHistoryDTO;
import uz.kun.entity.ProfileEntity;
import uz.kun.entity.SmsSendHistoryEntity;
import uz.kun.enums.ProfileRole;
import uz.kun.enums.ProfileStatus;
import uz.kun.exception.AppBadException;
import uz.kun.repository.ProfileRepository;
import uz.kun.repository.SmsSendHistoryRepository;
import uz.kun.utils.JWTUtil;
import uz.kun.utils.MDUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SmsHistoryService {

    static final Random random=new Random();

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private SmsSendHistoryRepository smsSendHistoryRepository;

    @Autowired
    private MailSenderService mailSenderService;

    public String  register(ProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optional.get());
            } else {
                throw new AppBadException("Email exists");
            }
        }
        if(!(dto.getEmail().length()>11)){
            throw new AppBadException("Invalid Mail");
        }
        if(!(dto.getName().length()>3)){
            throw new AppBadException("Invalid name");
        }
        if(!(dto.getSurname().length()>3)){
            throw new AppBadException("Invalid surname");
        }
        if(!(dto.getPassword().length()>3)){
            throw new AppBadException("Invalid password");
        }
//        if(!(dto.getPhone().length()>4)){
//            throw new AppBadException("Invalid phone");
//        }
        StringBuilder smsCode= new StringBuilder();
        for (int i = 0; i < 4; i++) {
            smsCode.append(random.nextInt(1,9));
        }

        ProfileEntity entity=new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MDUtil.encode(dto.getPassword()));
        entity.setRole(ProfileRole.USER);
        entity.setSmsCode(String.valueOf(smsCode));
        entity.setSmsCodeTime(LocalDateTime.now());
        entity.setStatus(ProfileStatus.REGISTRATION);

        profileRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        sendMessage(entity.getSmsCode());

//        mailSenderService.send(entity.getPhone(),"hello",entity.getSmsCode());
        return "We have sent a verifying code to you Email: "+entity.getPhone()+" code is - <<"+entity.getSmsCode()+">>";
    }

    public Boolean sendMessage(String code){
        return true;
    }

    public String verify(ProfileDTO verificationDTO) {
        Optional<ProfileEntity> profile = profileRepository.findByPhone(verificationDTO.getPhone());
        if (profile.isEmpty()) {
            throw new AppBadException("Profile not found");
        }
        ProfileEntity entity = profile.get();

        SmsSendHistoryEntity smsSendHistoryEntity=new SmsSendHistoryEntity();
        if (entity.getStatus().equals(ProfileStatus.BLOCK)) {
            throw  new AppBadException("Profile has been blocked!");
        }

        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw  new AppBadException("!ProfileStatus.Inactive");
        }

        LocalDateTime smsTime = entity.getSmsCodeTime();

        LocalDateTime now = LocalDateTime.now();
        boolean limit = smsTime.isAfter(now.minusMinutes(1));
        if (!limit) {
            throw new AppBadException("SMS timeout");
        }
        if (!entity.getSmsCode().equals(verificationDTO.getSmsCode())) {
            throw new AppBadException("Code is wrong");
        }

        Integer register = profileRepository.register(entity.getPhone());

        ProfileDTO dto = new ProfileDTO();
        dto.setJwt(JWTUtil.encode(entity.getId(), entity.getRole()));

        smsSendHistoryEntity.setMessage(entity.getSmsCode());
        smsSendHistoryEntity.setUserPhone(entity.getPhone());

        smsSendHistoryRepository.save(smsSendHistoryEntity);

        return "SUCCESS\n" + "JWT : "+dto.getJwt();
    }

    public List<SmsHistoryDTO> getByPhone(String phone){
        List<SmsSendHistoryEntity> list = smsSendHistoryRepository.getByUserPhone(phone);
        if(list.isEmpty()){
            throw new AppBadException("History not found with this phone!!!");
        }
        return toDto(list);
    }

    public List<SmsHistoryDTO> getByDate(LocalDate localDate){
        LocalDateTime fromDate=LocalDateTime.of(localDate, LocalTime.MIN);
        LocalDateTime toDate=LocalDateTime.of(localDate, LocalTime.MAX);
        List<SmsSendHistoryEntity> list = smsSendHistoryRepository.findAllByCreatedDateBetween(fromDate,toDate);
        if(list.isEmpty()){
            throw new AppBadException("History not found with this phone!!!");
        }
        return toDto(list);
    }

    public PageImpl<SmsHistoryDTO> pagination(Integer page, Integer size) {

        Pageable pageable= PageRequest.of(page-1,size);

        Page<SmsSendHistoryEntity> pagination = smsSendHistoryRepository.findAllBy(pageable);

        List<SmsSendHistoryEntity> content = pagination.getContent();
        long totalElements = pagination.getTotalElements();

        List<SmsHistoryDTO> dtoList = toDto(content);


        return new PageImpl<>(dtoList,pageable,totalElements);
    }




    public List<SmsHistoryDTO> toDto(List<SmsSendHistoryEntity> entityList){

        List<SmsHistoryDTO> dtoList=new LinkedList<>();
        for (SmsSendHistoryEntity entity : entityList) {
            SmsHistoryDTO dto=new SmsHistoryDTO();
            dto.setMessage(entity.getMessage());
            dto.setUserPhone(entity.getUserPhone());
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        return dtoList;
    }



}
