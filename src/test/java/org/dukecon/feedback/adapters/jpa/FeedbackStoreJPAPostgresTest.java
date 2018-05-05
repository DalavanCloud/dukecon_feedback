package org.dukecon.feedback.adapters.jpa;

import org.dukecon.feedback.adapters.main.DukeconFeedbackMain;
import org.dukecon.feedback.domain.FeedbackStore;
import org.dukecon.feedback.domain.model.ConferenceId;
import org.dukecon.feedback.domain.model.Feedback;
import org.dukecon.feedback.domain.model.TalkId;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("postgresql-local")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DukeconFeedbackMain.class)
@ContextConfiguration(initializers = FeedbackStoreJPAPostgresTest.Initializer.class)
public class FeedbackStoreJPAPostgresTest {

    //    @Value("${postgres.dbname}")
    private static String POSTGRES_DB_NAME = "dukecon";

    //    @Value("${spring.datasource.username}")
    private static String POSTGRES_USERNAME = "dukecon";

    //    @Value("${spring.datasource.password}")
    private static String POSTGRES_PASSWORD = "dukecon";

    @Autowired
    FeedbackStore feedbackStore;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:9.6.2")
            .withDatabaseName(POSTGRES_DB_NAME)
            .withUsername(POSTGRES_USERNAME)
            .withPassword(POSTGRES_PASSWORD);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "postgres.host=" + postgreSQLContainer.getContainerIpAddress(),
                    "postgres.port=" + postgreSQLContainer.getMappedPort(5432)
                )
                .applyTo(configurableApplicationContext.getEnvironment()
            );
        }
    }

    @Test
    public void testQueryString() {
        final String testQueryString = postgreSQLContainer.getTestQueryString();
        assertThat(testQueryString).isEqualToIgnoringCase("select 1");
    }

    @Test
    public void testToSave() {
        ConferenceId conferenceId = ConferenceId.of("javaland2018");
        TalkId talkId = TalkId.of("12345");
        Feedback feedback
                = Feedback
                .builder()
                .author("Gerd Aschemann")
                .conferenceId(conferenceId)
                .talkId(talkId)
                .comment("No Comment")
                .build();
        feedbackStore.save(feedback);
    }
}
