package com.educationalplatform.service.interfaces;

import com.educationalplatform.dto.UserDto;
import com.educationalplatform.entity.User;
import com.educationalplatform.enums.Role;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto userDto);
     UserDto getUserById(Long id);
     List<UserDto> getAllUsers();
     UserDto updateUser(Long id, UserDto userDto);
     String deleteUser(Long id);
    List<UserDto> findByRole(Role role);
    UserDto findByEmail(String email);
}
