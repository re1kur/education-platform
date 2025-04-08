package re1kur.authenticationservice.mapper;

import re1kur.authenticationservice.dto.UserDto;
import re1kur.authenticationservice.dto.UserWriteDto;
import re1kur.authenticationservice.entity.User;

public interface UserMapper {
    User write(UserWriteDto from);

    UserDto read(User user);
}
