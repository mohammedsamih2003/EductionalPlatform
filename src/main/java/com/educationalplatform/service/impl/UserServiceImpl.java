package com.educationalplatform.service.impl;

import com.educationalplatform.dto.UserDto;
import com.educationalplatform.entity.User;
import com.educationalplatform.enums.Role;
import com.educationalplatform.repositories.UserRepository;
import com.educationalplatform.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto addUser(UserDto userDto) {
        User user = new User(
                userDto.getUsername(),
                userDto.getEmail(),
                "moooo14452",
                userDto.getRole()
        );
        User savedUser = userRepository.save(user);

        UserDto response = new UserDto(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );

        return response;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDto response = new UserDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getRole()
        );

        return response;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users){
            UserDto userDto = new UserDto(
              user.getId(),
              user.getUserName(),
              user.getEmail(),
              user.getRole()
            );
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));

        user.setUserName(userDto.getUsername());
        user.setEmail(userDto.getEmail());;
        user.setRole(userDto.getRole());

        User updateUser = userRepository.save(user);
        UserDto response = new UserDto(
                updateUser.getId(),
                updateUser.getUserName(),
                updateUser.getEmail(),
                updateUser.getRole()
        );
        return response;
    }

    @Override
    public String deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
        userRepository.delete(user);

        return  "User deleted with id = " + id;
    }

    @Override
    public List<UserDto> findByRole(Role role) {
        List<User> users=userRepository.findByRole(role);

        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users){
            UserDto userDto = new UserDto(
                    user.getId(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getRole()
            );
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDto response = new UserDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getRole()
        );

        return response;
    }
}
