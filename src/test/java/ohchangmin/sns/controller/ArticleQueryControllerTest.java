package ohchangmin.sns.controller;

import ohchangmin.sns.service.response.ArticleElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleQueryControllerTest extends ControllerIntegrationTestSupport {

    @DisplayName("나의 게시글 목록을 조회할 수 있다.")
    @Test
    void findArticles() throws Exception {
        //given
        ArticleElement articleElement1 = ArticleElement.builder().id(1L).username("user").title("제목1").thumbnail("이미지 경로1").build();
        ArticleElement articleElement2 = ArticleElement.builder().id(2L).username("user").title("제목2").thumbnail("이미지 경로2").build();

        given(articleQueryService.findArticles(ArgumentMatchers.anyLong()))
                .willReturn(List.of(articleElement1, articleElement2));

        //when //then
        mockMvc.perform(get("/users/{userId}/articles", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].username").value("user"))
                .andExpect(jsonPath("$.data[0].title").value("제목1"))
                .andExpect(jsonPath("$.data[0].thumbnail").value("이미지 경로1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].username").value("user"))
                .andExpect(jsonPath("$.data[1].title").value("제목2"))
                .andExpect(jsonPath("$.data[1].thumbnail").value("이미지 경로2"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}