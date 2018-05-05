package org.dukecon.feedback.domain;

import org.dukecon.feedback.domain.model.Feedback;

public interface FeedbackStore {
    public void save (Feedback feedback);
}
