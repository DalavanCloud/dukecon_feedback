package org.dukecon.feedback.domain.model;

import lombok.Value;

import javax.validation.constraints.Size;

@Value(staticConstructor="of")
public class ConferenceId {
    @Size(max = 255)
    private String id;
}
