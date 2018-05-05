package org.dukecon.feedback.adapters.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackInput {
    private int rating;
    @Size(max = 4096)
    private String comment;
}