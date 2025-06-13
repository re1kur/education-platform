package re1kur.authenticationservice.mapper.impl;

import payload.UserPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import dto.UserDto;
import re1kur.authenticationservice.entity.User;
import re1kur.authenticationservice.mapper.UserMapper;

@Component
@RequiredArgsConstructor
public class DefaultUserMapper implements UserMapper {
    private final BCryptPasswordEncoder encoder;

    @Override
    public User write(UserPayload from) {
        return User.builder()
                .email(from.email())
                .password(encoder.encode(from.password()))
                .build();
    }

    @Override
    public UserDto read(User from) {
        return UserDto.builder()
                .id(from.getId().toString())
                .email(from.getEmail())
                .isEmailVerified(from.getIsEmailVerified())
                .build();
    }
}
