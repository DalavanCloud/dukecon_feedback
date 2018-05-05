package org.dukecon.feedback.adapters.jpa;

import org.dukecon.feedback.adapters.jpa.model.FeedbackEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepositoryJpa extends CrudRepository<FeedbackEntity, Long> {
}
