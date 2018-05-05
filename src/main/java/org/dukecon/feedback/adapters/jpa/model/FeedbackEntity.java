package org.dukecon.feedback.adapters.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "feedback")
public class FeedbackEntity {

    @Id
    @GeneratedValue // (strategy=SEQUENCE, generator="feedback_id_seq")
    private Long id;

    @Column
    private String author;

    @Column
    private String conference;

    @Column
    private String talk;

    @Column
    private String comment;

    @Column
    private Integer rating;
}
