package org.dukecon.feedback.adapters.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dukecon.feedback.adapters.rest.FeedbackInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WithMockUser(roles = {"user"})
class SpringRestDocsTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(RestDocumentationExtension restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    void sendFeedback() throws Exception {
        FeedbackInput feedback = new FeedbackInput(3, "test");

        final MvcResult result = this.mockMvc.perform(put("/rest/feedback/event/{conferenceId}/{eventId}", "javaland2018", "111")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(feedback)))
                .andExpect(status().isCreated())
                .andDo(document("send-feedback", requestFields(fieldWithPath("rating").description("The talk's rating"),
                        fieldWithPath("comment").description("free comment of attendee about this event"))))
                .andDo(document("send-feedback", pathParameters(
                        parameterWithName("conferenceId").description("The conference's id"),
                        parameterWithName("eventId").description("The event's id")
                )))
                .andReturn();
        Assertions.assertEquals("", result.getResponse().getContentAsString());
    }

    @Test
    void getFeedback() throws Exception {
        this.mockMvc.perform(get("/rest/feedback/event/{conferenceId}/{eventId}", "javaland2018", "111")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(document("get-feedback", pathParameters(
                        parameterWithName("conferenceId").description("The conference's id"),
                        parameterWithName("eventId").description("The event's id")
                )));
    }
}
