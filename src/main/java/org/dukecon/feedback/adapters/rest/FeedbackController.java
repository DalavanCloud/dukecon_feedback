package org.dukecon.feedback.adapters.rest;

import org.dukecon.feedback.domain.DukeconFeedbackApplication;
import org.dukecon.feedback.domain.model.ConferenceId;
import org.dukecon.feedback.domain.model.Feedback;
import org.dukecon.feedback.domain.model.TalkId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("rest/feedback")
@RestController
@PreAuthorize("isFullyAuthenticated() && hasRole('user')")
public class FeedbackController {

    @Autowired
    private DukeconFeedbackApplication dukeconFeedbackApplication;

    @PutMapping("event/{conferenceId}/{eventId}")
    public ResponseEntity sendFeedback(@PathVariable("conferenceId") String conferenceId, @PathVariable("eventId") String eventId, @RequestBody @Valid FeedbackInput feedbackInput) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Feedback feedback
                = Feedback.builder()
                .author(authentication.getName())
                .conferenceId(ConferenceId.of(conferenceId))
                .talkId(TalkId.of(eventId))
                .comment(feedbackInput.getComment())
                .rating(feedbackInput.getRating())
                .build();
        dukeconFeedbackApplication.save(feedback);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("event/{conferenceId}/{eventId}")
    public String existsFeedback(@PathVariable("conferenceId") String conferenceId, @PathVariable("eventId") String eventId) {
        System.out.println(String.format("feedback for %s/%s (Authentication: %s)", conferenceId, eventId, SecurityContextHolder.getContext().getAuthentication()));
        return "Ok (Dummy data)";
    }
}

