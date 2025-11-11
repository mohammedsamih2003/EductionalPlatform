package com.educationalplatform.service.impl;

import com.educationalplatform.dto.UserDto;
import com.educationalplatform.entity.User;
import com.educationalplatform.enums.Role;
import com.educationalplatform.repositories.UserRepository;
import com.educationalplatform.service.interfaces.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDto addUser(UserDto userDto) {

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        UserDto response = modelMapper.map(savedUser, UserDto.class);
        return response;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDto response = modelMapper.map(user, UserDto.class);
        return response;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users){
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));

        modelMapper.map(userDto, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User updateUser = userRepository.save(user);
        UserDto response = modelMapper.map(updateUser, UserDto.class);

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
            UserDto userDto =modelMapper.map(user, UserDto.class);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDto response =  modelMapper.map(user, UserDto.class);

        return response;
    }
}
