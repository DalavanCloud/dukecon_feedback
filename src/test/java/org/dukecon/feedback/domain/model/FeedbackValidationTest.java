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

public class FeedbackValidationTest {

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
    public void testCorrectFeedback() {
        ConferenceId conferenceId = ConferenceId.of("javaland2018");
        TalkId talkId = TalkId.of("12345");
        Feedback feedback
                = Feedback
                .builder()
                .author("Max Mustermann")
                .conferenceId(conferenceId)
                .talkId(talkId)
                .comment("No Comment")
                .build();

        Set<ConstraintViolation<Feedback>> violations
                = validator.validate(feedback);

        assertThat(violations).isEmpty();
    }

    @Test
    public void testAuthorNameToLong() {
        ConferenceId conferenceId = ConferenceId.of("javaland2018");
        TalkId talkId = TalkId.of("12345");
        String authorName = randomString(256);
        Feedback feedback
                = Feedback
                .builder()
                .author(authorName)
                .conferenceId(conferenceId)
                .talkId(talkId)
                .comment("No Comment")
                .build();

        Set<ConstraintViolation<Feedback>> violations
                = validator.validate(feedback);

        assertThat(violations.size()).isEqualTo(1);

        ConstraintViolation<Feedback> violation
                = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualToIgnoringCase("author");
    }

    @Test
    public void testCommentToLong() {
        ConferenceId conferenceId = ConferenceId.of("javaland2018");
        TalkId talkId = TalkId.of("12345");
        String comment = randomString(4097);
        Feedback feedback
                = Feedback
                .builder()
                .author("Max Mustermann")
                .conferenceId(conferenceId)
                .talkId(talkId)
                .comment(comment)
                .build();

        Set<ConstraintViolation<Feedback>> violations
                = validator.validate(feedback);

        assertThat(violations.size()).isEqualTo(1);

        ConstraintViolation<Feedback> violation
                = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualToIgnoringCase("comment");
    }

    @Test
    public void testAuthorAndCommentToLong() {
        ConferenceId conferenceId = ConferenceId.of("javaland2018");
        TalkId talkId = TalkId.of("12345");
        String authorName = randomString(256);
        String comment = randomString(4097);
        Feedback feedback
                = Feedback
                .builder()
                .author(authorName)
                .conferenceId(conferenceId)
                .talkId(talkId)
                .comment(comment)
                .build();

        Set<ConstraintViolation<Feedback>> violations
                = validator.validate(feedback);

        assertThat(violations.size()).isEqualTo(2);
    }
}
