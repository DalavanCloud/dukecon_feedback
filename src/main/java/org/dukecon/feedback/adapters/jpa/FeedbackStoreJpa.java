package org.dukecon.feedback.adapters.jpa;

import org.dukecon.feedback.adapters.jpa.model.FeedbackEntity;
import org.dukecon.feedback.domain.FeedbackStore;
import org.dukecon.feedback.domain.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackStoreJpa implements FeedbackStore {
    @Autowired
    FeedbackRepositoryJpa feedbackRepositoryJpa;

    @Override
    public void save(Feedback feedback) {
        FeedbackEntity entity
            = FeedbackEntity
                .builder()
                .author(feedback.getAuthor())
                .conference(feedback.getConferenceId().getId())
                .talk(feedback.getTalkId().getId())
                .rating(feedback.getRating())
                .comment(feedback.getComment())
                .build();
        feedbackRepositoryJpa.save(entity);
    }
}
