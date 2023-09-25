package ohchangmin.sns.controller;

import ohchangmin.sns.controller.security.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FollowControllerTest extends ControllerIntegrationTestSupport {

    @WithMockCustomUser
    @DisplayName("다른 유저를 팔로우 할 수 있다.")
    @Test
    void follow() throws Exception {

        //when //then
        mockMvc.perform(post(("/users/{userId}/follow"), 1L)
                        .header("Authorization", AUTH_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("follow",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @WithMockCustomUser
    @DisplayName("다른 유저를 팔로우 할 수 있다.")
    @Test
    void unfollow() throws Exception {
        //when //then
        mockMvc.perform(post(("/users/{userId}/unfollow"), 1L)
                        .header("Authorization", AUTH_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("unfollow",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

}