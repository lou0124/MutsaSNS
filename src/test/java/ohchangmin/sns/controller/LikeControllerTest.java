package ohchangmin.sns.controller;

import ohchangmin.sns.controller.security.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LikeControllerTest extends ControllerIntegrationTestSupport {

    @WithMockCustomUser
    @DisplayName("좋아요 요청을 할 수 있다.")
    @Test
    void requestLike() throws Exception {

        //when //then
        mockMvc.perform(post(("/articles/{articleId}/likes"), 1L)
                        .header("Authorization", AUTH_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("like-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}