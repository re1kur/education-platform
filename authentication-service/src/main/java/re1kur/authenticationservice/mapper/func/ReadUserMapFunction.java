package re1kur.authenticationservice.mapper.func;

import re1kur.authenticationservice.dto.UserDto;
import re1kur.authenticationservice.entity.User;

import java.util.function.Function;

public class ReadUserMapFunction implements Function<User, UserDto> {
    /**
     * Applies this function to the given argument.
     *
     * @param user the function argument
     * @return the function result
     */
    @Override
    public UserDto apply(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .isEmailVerified(user.getIsEmailVerified())
                .build();
    }
}
