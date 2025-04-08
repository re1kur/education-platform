package re1kur.authenticationservice.mapper.func;

import re1kur.authenticationservice.dto.UserWriteDto;
import re1kur.authenticationservice.entity.Role;
import re1kur.authenticationservice.entity.User;

import java.util.List;
import java.util.function.Function;

public class RegisterUserMapFunction implements Function<UserWriteDto, User> {
    /**
     * Applies this function to the given argument.
     *
     * @param userWriteDto the function argument
     * @return the function result
     */
    @Override
    public User apply(UserWriteDto userWriteDto) {
        return User.builder()
                .email(userWriteDto.getEmail())
                .password(userWriteDto.getPassword())
                .roles(List.of(Role.builder()
                        // TODO get the role of user for default insert
                        .id(1)
                        .name("USER")
                        .build()))
                .build();
    }
}
