package re1kur.authenticationservice.jwt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import re1kur.authenticationservice.entity.Role;
import re1kur.authenticationservice.entity.User;
import re1kur.authenticationservice.jwt.entity.Credentials;
import re1kur.authenticationservice.jwt.entity.Token;
import re1kur.authenticationservice.jwt.impl.DefaultJwtGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(
        properties = {
                "custom.jwt.privateKeyPath=test-private.key",
                "custom.jwt.publicKeyPath=test-public.key",
                "custom.jwt.keySize=2048"
        })
class DefaultJwtGeneratorTest {

    @InjectMocks
    private DefaultJwtGenerator generator;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(generator, "privateKeyPath", "test-private.key");
        ReflectionTestUtils.setField(generator, "publicKeyPath", "test-public.key");
        ReflectionTestUtils.setField(generator, "keySize", 2048);
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("test-private.key"));
        Files.deleteIfExists(Path.of("test-public.key"));
    }

    @Test
    public void testGenerate() {
        User user = User.builder()
                .id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .email("email@example.com")
                .isEmailVerified(true)
                .roles(List.of(Role.builder().name("USER").build()))
                .build();
        Credentials credentials = new Credentials(user);

        Token token = generator.generate(credentials);

        Assertions.assertNotNull(token);
        Assertions.assertTrue(token.body().startsWith("eyJ"));
    }
}