package ohchangmin.sns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ohchangmin.sns.controller.request.ArticleCreateRequest;
import ohchangmin.sns.controller.security.WithMockCustomUser;
import ohchangmin.sns.file.FileStore;
import ohchangmin.sns.jwt.JwtTokenFilter;
import ohchangmin.sns.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = ArticleController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = JwtTokenFilter.class
                )
        })
@AutoConfigureMockMvc(addFilters = false)
class ArticleControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean ArticleService articleService;
    @MockBean FileStore fileStore;

    @WithMockCustomUser
    @DisplayName("회원가입을 할 수 있다.")
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