package dev.dunglv202.techmaster.mapper;

import dev.dunglv202.techmaster.dto.req.RegistrationInfo;
import dev.dunglv202.techmaster.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(RegistrationInfo registrationInfo);
}
