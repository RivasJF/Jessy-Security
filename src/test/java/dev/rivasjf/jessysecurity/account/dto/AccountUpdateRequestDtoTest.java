package dev.rivasjf.jessysecurity.account.dto;

import dev.rivasjf.jessysecurity.account.dto.request.AccountAdditionalInformationUpdateRequestDto;
import dev.rivasjf.jessysecurity.account.dto.request.AccountUpdateRequestDto;
import dev.rivasjf.jessysecurity.account.entitie.AdditionalInformationType;
import dev.rivasjf.jessysecurity.account.entitie.CategoryAccount;
import dev.rivasjf.jessysecurity.auth.dto.UserRegisterRequestDtoTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountUpdateRequestDtoTest {

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
        List<AccountAdditionalInformationUpdateRequestDto> additionalInfoList = List.of(
                new AccountAdditionalInformationUpdateRequestDto(
                        "i1",
                        true,
                        AdditionalInformationType.PASSWORD,
                        "4168834678",
                        "43789329"
                ), new AccountAdditionalInformationUpdateRequestDto(
                        "i2",
                        true,
                        AdditionalInformationType.EMAIL,
                        "4168834678",
                        "43789329"
                ));
        var dto = new AccountUpdateRequestDto(
                "i1",
                "AccountTest",
                "user@example.com",
                "This is a test account",
                CategoryAccount.SOCIAL_MEDIA,
                additionalInfoList
                );
        Set<ConstraintViolation<AccountUpdateRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void validBlank_noViolations() {
        var dto = new AccountUpdateRequestDto(
                "i1",
                null,
                null,
                null,
                CategoryAccount.SOCIAL_MEDIA,
                null
        );
        Set<ConstraintViolation<AccountUpdateRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void validTrimmed_noViolations() {
        List<AccountAdditionalInformationUpdateRequestDto> additionalInfoList = List.of(
                new AccountAdditionalInformationUpdateRequestDto(
                        "i1                ",
                        true,
                        AdditionalInformationType.PASSWORD,
                        "4168834678            ",
                        "               43789329"
                ), new AccountAdditionalInformationUpdateRequestDto(
                        "    i2",
                        true,
                        AdditionalInformationType.EMAIL,
                        "4168834678  ",
                        "43789329"
                ));
        var dto = new AccountUpdateRequestDto(
                "i1          ",
                "AccountTest            ",
                "user@example.com",
                "           This is a test account",
                CategoryAccount.SOCIAL_MEDIA,
                additionalInfoList
        );
        Set<ConstraintViolation<AccountUpdateRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void InvalidateData_hasViolations() {
        var Text = "Go es un lenguaje de programación concurrente y compilado con tipado " +
                "estático inspirado en la sintaxis de C, pero con seguridad de memoria y " +
                "recolección de basura. Ha sido desarrollado por Google\u200B y sus diseñadores " +
                "iniciales fueron Robert Griesemer, Rob Pike y Ken Thompson.\u200B";
        List<AccountAdditionalInformationUpdateRequestDto> additionalInfoList = List.of(
                new AccountAdditionalInformationUpdateRequestDto(
                        null,
                        true,
                        AdditionalInformationType.PASSWORD,
                        "4168834678",
                        "43789329"
                ), new AccountAdditionalInformationUpdateRequestDto(
                        "i2",
                        true,
                        null,
                        "4168834678  ",
                        "43789329"
                ), new AccountAdditionalInformationUpdateRequestDto(
                        "i2",
                        true,
                        null,
                        "4168834678  ",
                        null
                ));
        var dto = new AccountUpdateRequestDto(
                "i1          ",
                Text,
                Text,
                Text + Text + Text,
                null,
                additionalInfoList
        );
        Set<ConstraintViolation<AccountUpdateRequestDto>> violations = validator.validate(dto);
        log.info(() -> "Violations: " + violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", ")));
        assertEquals(3, violations.size());
    }

    @Test
    void validNullValues_hasViolations() {
        List<AccountAdditionalInformationUpdateRequestDto> additionalInfoList = List.of(
                new AccountAdditionalInformationUpdateRequestDto(
                        null,
                        true,
                        AdditionalInformationType.PASSWORD,
                        "4168834678",
                        "43789329"
                ), new AccountAdditionalInformationUpdateRequestDto(
                        "i2",
                        false,
                        AdditionalInformationType.EMAIL,
                        "4168834678  ",
                        "43789329"
                ));
        var dto = new AccountUpdateRequestDto(
                "i1",
                "AccountTest",
                "user@example.com",
                "This is a test account",
                CategoryAccount.SOCIAL_MEDIA,
                additionalInfoList
        );
        Set<ConstraintViolation<AccountUpdateRequestDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }

}
