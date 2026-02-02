package com.chungdt03.holashopbe.services;

import com.chungdt03.holashopbe.dtos.RefreshTokenDTO;
import com.chungdt03.holashopbe.dtos.UpdateUserDTO;
import com.chungdt03.holashopbe.dtos.UserDTO;
import com.chungdt03.holashopbe.exceptions.payload.DataNotFoundException;
import com.chungdt03.holashopbe.models.User;
import com.chungdt03.holashopbe.responses.user.LoginResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User createUser(UserDTO userDTO) throws Exception;

    String login(String phoneNumber, String password) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;

    User updateUser(Long userId, UpdateUserDTO updateUserDTO) throws Exception;

    LoginResponse refreshToken(RefreshTokenDTO refreshTokenDTO);

    Page<User> findAllUsers(String keyword, Pageable pageable);

    void resetPassword(Long userId, String newPassword) throws Exception;

    void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException;
}
