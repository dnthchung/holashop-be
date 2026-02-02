package com.chungdt03.holashopbe.mapper;

import com.chungdt03.holashopbe.dtos.UserDTO;
import com.chungdt03.holashopbe.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserDTO userDTO);
}
