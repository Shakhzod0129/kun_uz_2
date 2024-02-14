package uz.kun.service;


import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.kun.dto.*;
import uz.kun.entity.EmailSendHistoryEntity;
import uz.kun.entity.ProfileEntity;
import uz.kun.enums.ProfileRole;
import uz.kun.enums.ProfileStatus;
import uz.kun.exception.AppBadException;
import uz.kun.repository.EmailSendHistoryRepository;
import uz.kun.repository.ProfileRepository;
import uz.kun.utils.JWTUtil;
import uz.kun.utils.MDUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EmailHistoryService {



    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private MailSenderService mailSender;
    @Autowired
    private EmailSendHistoryRepository emailSendHistoryRepository;

    public Boolean registration(RegistrationDTO dto) { // login
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optional.get());
            } else {
                throw new AppBadException("Email exists");
            }
        }

        LocalDateTime from=LocalDateTime.now().minusMinutes(1);
        LocalDateTime to=LocalDateTime.now();

        if (emailSendHistoryRepository.countSendEmail(dto.getEmail(), from, to) >= 3) {
            throw new AppBadException("To many attempt. Please try after 1 minute.");
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
        if(!(dto.getPhone().length()>4)){
            throw new AppBadException("Invalid phone");
        }

        // create
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MDUtil.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setPhone(dto.getPhone());
        //send verification code (email/sms)
        String text = "<h1 style=\"text-align: center\">Hello %s</h1>\n" +
                "<p style=\"background-color: indianred; color: white; padding: 30px\">To complete registration please link to the following link</p>\n" +
                "<a style=\" background-color: #f44336;\n" +
                "  color: white;\n" +
                "  padding: 14px 25px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\" href=\"http://localhost:8080/auth/verification/email/%s\n" +
                "\">Click</a>\n" +
                "<br>\n";
        EmailSendHistoryEntity emailSendHistoryEntity=new EmailSendHistoryEntity();

        emailSendHistoryEntity.setMessage(text);
        emailSendHistoryEntity.setCreatedDate(LocalDateTime.now());
        emailSendHistoryEntity.setEmail(entity.getEmail());

        emailSendHistoryRepository.save(emailSendHistoryEntity);
        profileRepository.save(entity);
        String jwt = JWTUtil.encodeForEmail(entity.getId());

        text = String.format(text, entity.getName(), jwt);


        mailSender.sendEmail(dto.getEmail(), "Complete registration", text);
        return true;
    }


    public Boolean emailVerification(String jwt) {
        try {
            JWTDTO jwtDTO = JWTUtil.decode(jwt);

            Optional<ProfileEntity> optional = profileRepository.findById(jwtDTO.getId());
            if (!optional.isPresent()) {
                throw new AppBadException("Profile not found");
            }
            ProfileEntity entity = optional.get();
            if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
                throw new AppBadException("Profile in wrong status");
            }
            profileRepository.updateStatus(entity.getId(), ProfileStatus.ACTIVE);

        } catch (JwtException e) {
            throw new AppBadException("Please tyre again.");
        }
        return true;
    }

    public List<EmailHistoryDTO> getByEmail(String email){

        List<EmailSendHistoryEntity> list = emailSendHistoryRepository.getByEmail(email );
        if(list.isEmpty()){
            throw new AppBadException("History not found with this phone!!!");
        }
        return toDto(list);
    }

    public List<EmailHistoryDTO> getByDate(LocalDate localDate){
        LocalDateTime fromDate=LocalDateTime.of(localDate, LocalTime.MIN);
        LocalDateTime toDate=LocalDateTime.of(localDate, LocalTime.MAX);
        List<EmailSendHistoryEntity> list = emailSendHistoryRepository.findAllByCreatedDateBetween(fromDate,toDate);
        if(list.isEmpty()){
            throw new AppBadException("History not found with this phone!!!");
        }
        return toDto(list);
    }

    public PageImpl<EmailHistoryDTO> pagination(Integer page, Integer size) {

        Pageable pageable= PageRequest.of(page-1,size);

        Page<EmailSendHistoryEntity> pagination = emailSendHistoryRepository.findAllBy(pageable);

        List<EmailSendHistoryEntity> content = pagination.getContent();
        long totalElements = pagination.getTotalElements();

        List<EmailHistoryDTO> dtoList = toDto(content);


        return new PageImpl<>(dtoList,pageable,totalElements);
    }




    public List<EmailHistoryDTO> toDto(List<EmailSendHistoryEntity> entityList){

        List<EmailHistoryDTO> dtoList=new LinkedList<>();
        for (EmailSendHistoryEntity entity : entityList) {
            EmailHistoryDTO dto=new EmailHistoryDTO();
            dto.setMessage(entity.getMessage());
            dto.setEmail(entity.getEmail());
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        return dtoList;
    }






}
