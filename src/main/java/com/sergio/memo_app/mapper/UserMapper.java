package com.sergio.memo_app.mapper;

import com.sergio.memo_app.api.dto.UserDto;
import com.sergio.memo_app.mapper.common.DateTimeHandler;
import com.sergio.memo_app.persistence.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends DateTimeHandler<UserDto> {

    @Mapping(source = "userDto", target = "createdAt", qualifiedByName = "now")
    @Mapping(source = "userDto", target = "updatedAt", qualifiedByName = "now")
    User toEntity(UserDto userDto);

    UserDto toDto(User user);
}
