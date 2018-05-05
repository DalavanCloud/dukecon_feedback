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

public class TalkIdValidationTest {
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
    public void testCorrectTalkId() {
        TalkId talkId = TalkId.of("12345");

        Set<ConstraintViolation<TalkId>> violations
                = validator.validate(talkId);

        assertThat(violations).isEmpty();
    }

    @Test
    public void testTalkIdToLong() {
        String talk = randomString(256);
        TalkId talkId = TalkId.of(talk);

        Set<ConstraintViolation<TalkId>> violations
                = validator.validate(talkId);

        assertThat(violations.size()).isEqualTo(1);

        ConstraintViolation<TalkId> violation
                = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualToIgnoringCase("id");
    }
}
