package re1kur.authenticationservice.mapper.impl;

import org.springframework.stereotype.Component;
import re1kur.authenticationservice.dto.UserDto;
import re1kur.authenticationservice.dto.UserWriteDto;
import re1kur.authenticationservice.entity.User;
import re1kur.authenticationservice.mapper.UserMapper;
import re1kur.authenticationservice.mapper.func.ReadUserMapFunction;
import re1kur.authenticationservice.mapper.func.RegisterUserMapFunction;

@Component
public class DefaultUserMapper implements UserMapper {
    private final RegisterUserMapFunction mapRegisterUser = new RegisterUserMapFunction();
    private final ReadUserMapFunction mapReadUser = new ReadUserMapFunction();

    @Override
    public User write(UserWriteDto from) {
        return mapRegisterUser.apply(from);
    }

    @Override
    public UserDto read(User user) {
        return mapReadUser.apply(user);
    }
}
