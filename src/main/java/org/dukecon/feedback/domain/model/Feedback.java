package org.dukecon.feedback.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Builder
@Data
public class Feedback {
    @NonNull
    @Size(max = 255)
    private String author;
    @NonNull
    private ConferenceId conferenceId;
    @NonNull
    private TalkId talkId;
    @Size(max = 4096)
    private String comment;
    private Integer rating;
}
