package dev.rivasjf.jessysecurity.auth.dto;

import dev.rivasjf.jessysecurity.auth.dto.request.LoginRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestDtoTest {

    private static final Logger log = LoggerFactory.getLogger(UserRegisterRequestDtoTest.class);
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setup() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @Test
    void validRequest_noViolations() {
        LoginRequestDto dto = new LoginRequestDto("user@example.com", "somePublicKey");
        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidEmailAndBlankPublicKey_hasViolations() {
        LoginRequestDto dto = new LoginRequestDto("invalid-email", "");
        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertEquals(2, violations.size());

        Set<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
        assertTrue(messages.contains("Email must to be valid"));
        assertTrue(messages.contains("PublicKey cannot be empty"));
    }

    @Test
    void BlankPublicKey_hasViolations() {
        LoginRequestDto dto = new LoginRequestDto("email@exampe.com     ", null);
        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        log.info(() -> "Violations: " + violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", ")));
        assertEquals(1, violations.size());
    }
}
