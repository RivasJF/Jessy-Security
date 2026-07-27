package dev.rivasjf.jessysecurity.auth.dto;

import dev.rivasjf.jessysecurity.auth.dto.request.UserRegisterRequestDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRegisterRequestDtoTest {

    private static final Logger log = LoggerFactory.getLogger(UserRegisterRequestDtoTest.class);
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void destroy() {
        factory.close();
    }

    @Test
    void validRequest_noViolations() {
        var dto = new UserRegisterRequestDto("rayos", "jon@gmail.com", "123456", "123456");
        Set<ConstraintViolation<UserRegisterRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidEmailAndBlankPublicKey_hasViolations() {
        var dto = new UserRegisterRequestDto(
                "as",
                "      jon@gmail.com",
                "       s",
                " ");
        Set<ConstraintViolation<UserRegisterRequestDto>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
    }
}
