package com.company.Lesson_62.service;

import com.company.Lesson_62.dto.ProfileDTO;
import com.company.Lesson_62.dto.auth.AuthorizationDTO;
import com.company.Lesson_62.dto.auth.RegistrationDTO;
import com.company.Lesson_62.entity.ProfileEntity;
import com.company.Lesson_62.enums.ProfileRole;
import com.company.Lesson_62.enums.ProfileStatus;
import com.company.Lesson_62.exeptions.BadRequestException;
import com.company.Lesson_62.exeptions.ItemNotFoundException;
import com.company.Lesson_62.repository.ProfileRepository;
import com.company.Lesson_62.util.JwtUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailService emailService;

    public ProfileDTO authorization(AuthorizationDTO dto) {
        String pswd = DigestUtils.md5Hex(dto.getPassword());
        Optional<ProfileEntity> optional = profileRepository.findByLoginAndPswd(dto.getLogin(),pswd);
        if (!optional.isPresent()){
            throw new RuntimeException("Login or password is incorrect");
        }
        if (!optional.get().getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new BadRequestException("You are not allowed");
        }

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(optional.get().getName());
        profileDTO.setSurname(optional.get().getSurname());
        profileDTO.setJwt(JwtUtil.createJwt(optional.get().getId(), optional.get().getRole()));

        return profileDTO;
    }


    public void registration(RegistrationDTO dto){

        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()){
            throw new BadRequestException("Email exists");
        }

        optional = profileRepository.findByLogin(dto.getLogin());
        if (optional.isPresent()){
            throw new BadRequestException("Login exists");
        }


        String pswd = DigestUtils.md5Hex(dto.getPswd());


        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setLogin(dto.getLogin());
        entity.setPswd(pswd);
        entity.setRole(ProfileRole.USER_ROLE);
        entity.setStatus(ProfileStatus.CREATED);
        profileRepository.save(entity);

        String jwt = JwtUtil.createJwt(entity.getId());
        StringBuilder builder = new StringBuilder();
        builder.append("Salom jigar qalaysan\n");
        builder.append("Agar bu sen bo'lsang shu linkga bos: ");
        builder.append("http://localhost:8080/auth/verification/"+ jwt);
        emailService.sendEmail(dto.getEmail(), "Registration KunUz Test", builder.toString());
    }

    public void verification(String jwt){
        Integer id = JwtUtil.decodeJwtAndGetId(jwt);
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (!optional.isPresent()){
            throw new ItemNotFoundException("User not found");
        }
        optional.get().setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(optional.get());
    }


}
