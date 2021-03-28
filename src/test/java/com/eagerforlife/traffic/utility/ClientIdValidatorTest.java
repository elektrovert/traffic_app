package com.eagerforlife.traffic.utility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class ClientIdValidatorTest {

    @Test
    void validateCorrectEmailFormatTest() {
        final ClientIdValidator validator = new ClientIdValidator();
        assertThat(validator.validateEmailFormat("test@test.com")).isTrue();
    }

    @Test
    void validateIncorrectEmailFormatTest() {
        final ClientIdValidator validator = new ClientIdValidator();
        assertThat(validator.validateEmailFormat("test@test.com.")).isFalse();
        assertThat(validator.validateEmailFormat("test@test..com")).isFalse();
        assertThat(validator.validateEmailFormat("test@test.com_")).isFalse();
        assertThat(validator.validateEmailFormat("test@test.com-")).isFalse();
        assertThat(validator.validateEmailFormat(".test@test.com")).isFalse();
        assertThat(validator.validateEmailFormat(
                new StringBuilder("12345678901234567890")
                        .append("12345678901234567890")
                        .append("12345678901234567890")
                        .append("12345678901234567890")
                        .append("12345678901234567890")
                        .append("@test.com")
                        .toString()))
                .isFalse();
    }

}
