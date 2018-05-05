package org.dukecon.feedback.adapters.jpa;

import org.dukecon.feedback.adapters.main.DukeconFeedbackMain;
import org.dukecon.feedback.domain.FeedbackStore;
import org.dukecon.feedback.domain.model.ConferenceId;
import org.dukecon.feedback.domain.model.Feedback;
import org.dukecon.feedback.domain.model.TalkId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = DukeconFeedbackMain.class) // FeedbackStoreJpa.class)
public class FeedbackStoreJpaTest {
    @Autowired
    FeedbackStore feedbackStore;

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
