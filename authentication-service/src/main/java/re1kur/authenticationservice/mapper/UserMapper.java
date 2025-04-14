package re1kur.authenticationservice.mapper;

import dto.UserDto;
import payload.UserPayload;
import re1kur.authenticationservice.entity.User;

public interface UserMapper {
    User write(UserPayload from);

    UserDto read(User user);

}
