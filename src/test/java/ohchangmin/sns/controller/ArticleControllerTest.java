package ohchangmin.sns.controller;

import ohchangmin.sns.controller.request.ArticleCreateRequest;
import ohchangmin.sns.controller.security.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleControllerTest extends ControllerIntegrationTestSupport {

    @WithMockCustomUser
    @DisplayName("피드를 등록 할 수 있다.")
    @Test
    void createArticle() throws Exception {
        // given
        ArticleCreateRequest request = ArticleCreateRequest.builder().title("제목 입니다.").content("내용 입니다.").build();

        //when //then
        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}