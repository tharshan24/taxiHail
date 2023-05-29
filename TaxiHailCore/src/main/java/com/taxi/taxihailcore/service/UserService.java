package com.taxi.taxihailcore.service;

import com.taxi.taxihailcore.dto.PasswordResetDTO;
import com.taxi.taxihailcore.dto.UserDTO;
import com.taxi.taxihailcore.model.User;
import com.taxi.taxihailcore.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<UserDTO> viewUser(UUID userId) {
        Optional<User> optionalUser = userRepository.findByUserIdAndStatus(userId, 1);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setPassword(null);
            UserDTO userDTO = new UserDTO();

            userDTO.setUserId(user.getUserId());
            userDTO.setUserName(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setMobile(user.getMobile());
            userDTO.setRole(user.getRole());
            userDTO.setStatus(user.getStatus());
            userDTO.setLastLogin(user.getLastLogin());
            userDTO.setCreatedAt(user.getCreatedAt());
            userDTO.setUpdatedAt(user.getUpdatedAt());

            return ResponseEntity.ok(userDTO);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity updateUser(UserDTO request, UUID userId) {
        try{

            userRepository.findByUserIdAndStatus(userId, 1).ifPresent(user -> {
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setEmail(request.getEmail());
                user.setMobile(request.getMobile());
                userRepository.save(user);
            });

            return ResponseEntity.status(HttpStatus.OK).body("User update successful");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User update failed\nException: " + e.toString());
        }
    }

    public ResponseEntity<String> resetPassword(PasswordResetDTO request, UUID userId) {
        Optional<User> optionalUser = userRepository.findByUserIdAndStatus(userId, 1);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();

            if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password Error");
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("Password Update Successful");

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Error");
    }
}
