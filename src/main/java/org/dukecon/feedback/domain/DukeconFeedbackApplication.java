package org.dukecon.feedback.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.dukecon.feedback.domain.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DukeconFeedbackApplication {

    @NonNull
    private final FeedbackStore repository;

    public void save (Feedback feedback) {
        repository.save(feedback);
    }
}
