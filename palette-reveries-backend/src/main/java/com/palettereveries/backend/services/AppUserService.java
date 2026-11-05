package com.palettereveries.backend.services;

import com.palettereveries.backend.dtos.AppUserDto;
import com.palettereveries.backend.dtos.CreateAppUserDto;
import com.palettereveries.backend.entities.AppUser;
import com.palettereveries.backend.entities.Role;
import com.palettereveries.backend.errors.ClientErrors;
import com.palettereveries.backend.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository,PasswordEncoder passwordEncoder){
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private AppUserDto toDto(AppUser user){
        return new AppUserDto(user.getId(),user.getUsername(),user.getRole(), user.getEmail());
    }

    public AppUserDto register (CreateAppUserDto createAppUserDto){
        if(appUserRepository.findAppUserByUsername(createAppUserDto.username()).isPresent()){
            throw ClientErrors.usernameTaken(createAppUserDto.username());
        }
        if(appUserRepository.findAppUserByEmail(createAppUserDto.email()).isPresent()){
            throw ClientErrors.emailTaken(createAppUserDto.email());
        }
        String hashed = passwordEncoder.encode(createAppUserDto.password());
        AppUser appUser = new AppUser(createAppUserDto.username(), hashed, Role.CUSTOMER, createAppUserDto.email());
        appUserRepository.save(appUser);
        return toDto(appUser);
    }

    public List<AppUserDto> getAllUsers(){
        return appUserRepository.findAll().stream().map(this::toDto).toList();
    }

    public AppUserDto getAppUser(Long id){
        return appUserRepository.findById(id).map(this::toDto).orElseThrow(()-> ClientErrors.userNotFound(id));

    }

    public void deleteUser(Long id){
        if(!appUserRepository.existsById(id)){
            throw ClientErrors.userNotFound(id);
        }
        appUserRepository.deleteById(id);

    }

}

