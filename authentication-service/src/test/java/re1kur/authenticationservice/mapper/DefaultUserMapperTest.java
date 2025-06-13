package re1kur.authenticationservice.mapper;

import dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import payload.UserPayload;
import re1kur.authenticationservice.entity.User;
import re1kur.authenticationservice.mapper.impl.DefaultUserMapper;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class DefaultUserMapperTest {
    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private DefaultUserMapper mapper;

    @Test
    public void testWriteMapping() {
        UserPayload payload = new UserPayload("email@example.com", "password");
        User user = User.builder()
                .email("email@example.com")
                .password("hashedPassword")
                .build();

        Mockito.when(encoder.encode("password")).thenReturn("hashedPassword");

        User mapped = mapper.write(payload);

        Assertions.assertEquals(mapped, user);

        Mockito.verify(encoder).encode("password");
    }

    @Test
    public void testReadMapping() {
        User user = User.builder()
                .id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .email("email@example.com")
                .isEmailVerified(null)
                .password("hashedPassword")
                .build();
        UserDto mustBe = UserDto.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .email("email@example.com")
                .isEmailVerified(null)
                .build();

        UserDto mapped = mapper.read(user);

        Assertions.assertEquals(mustBe, mapped);
    }
}