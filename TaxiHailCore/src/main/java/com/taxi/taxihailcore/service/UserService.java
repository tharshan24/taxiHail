package com.taxi.taxihailcore.service;

import com.taxi.taxihailcore.dto.UserDTO;
import com.taxi.taxihailcore.model.User;
import com.taxi.taxihailcore.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
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

            return ResponseEntity.status(1).body(userDTO);
        }
        else {
            return ResponseEntity.status(1).body(null);
        }
    }

    public ResponseEntity updateUser(User request) {
        try{

            userRepository.findByUserIdAndStatus(request.getUserId(), 1).ifPresent(user -> {
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setEmail(request.getEmail());
                user.setMobile(request.getMobile());
                user.setRole(request.getRole());
                userRepository.save(user);
            });

            return ResponseEntity.status(1).body("User update successful");

        } catch (Exception e) {
            return ResponseEntity.status(1).body("User update failed\nException: " + e.toString());
        }
    }
}
