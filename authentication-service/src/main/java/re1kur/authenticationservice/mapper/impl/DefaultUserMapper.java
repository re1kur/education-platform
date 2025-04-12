package re1kur.authenticationservice.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import re1kur.authenticationservice.dto.UserDto;
import re1kur.authenticationservice.dto.UserWriteDto;
import re1kur.authenticationservice.entity.Role;
import re1kur.authenticationservice.entity.User;
import re1kur.authenticationservice.mapper.UserMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultUserMapper implements UserMapper {
    private final BCryptPasswordEncoder encoder;

    @Override
    public User write(UserWriteDto from) {
        return User.builder()
                .email(from.getEmail())
                .password(encoder.encode(from.getPassword()))
                .roles(List.of(Role.builder()
                        // TODO get the role of user for default insert
                        .id(1)
                        .name("USER")
                        .build()))
                .build();
    }

    @Override
    public UserDto read(User from) {
        return UserDto.builder()
                .email(from.getEmail())
                .isEmailVerified(from.getIsEmailVerified())
                .build();
    }

}
