package org.dukecon.feedback.domain.model;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ConferenceIdValidationTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

    private String randomString(int lenghtOfString) {
        return RandomStringUtils.randomAlphabetic(lenghtOfString);
    }

    @Test
    public void testCorrectConferenceId() {
        ConferenceId conferenceId = ConferenceId.of("javaland2018");

        Set<ConstraintViolation<ConferenceId>> violations
                = validator.validate(conferenceId);

        assertThat(violations).isEmpty();
    }

    @Test
    public void testConferenceIdToLong() {
        String conferenceName = randomString(256);
        ConferenceId conferenceId = ConferenceId.of(conferenceName);

        Set<ConstraintViolation<ConferenceId>> violations
                = validator.validate(conferenceId);

        assertThat(violations.size()).isEqualTo(1);

        ConstraintViolation<ConferenceId> violation
                = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualToIgnoringCase("id");
    }
}
