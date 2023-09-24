package ohchangmin.sns.controller;

import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.ArticleImage;
import ohchangmin.sns.domain.Comment;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.service.response.ArticleElement;
import ohchangmin.sns.service.response.ArticleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.MediaType;

import java.util.List;

import static ohchangmin.sns.service.response.ArticleResponse.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleQueryControllerTest extends ControllerIntegrationTestSupport {

    @DisplayName("나의 피드 목록을 조회할 수 있다.")
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

    @DisplayName("피드 단건 조회를 할 수 있다.")
    @Test
    void findArticle() throws Exception {
        //given
        ArticleImageResponse articleImageResponse1 = ArticleImageResponse.builder().id(1L).imagesUrl("url1").build();
        ArticleImageResponse articleImageResponse2 = ArticleImageResponse.builder().id(2L).imagesUrl("url2").build();
        CommentResponse commentResponse1 = CommentResponse.builder().id(1L).username("dog").content("댓글1").build();
        CommentResponse commentResponse2 = CommentResponse.builder().id(2L).username("cat").content("댓글2").build();
        ArticleResponse articleResponse = builder()
                .id(1L)
                .username("user")
                .title("피드 제목")
                .content("피드 내용")
                .articleImages(List.of(articleImageResponse1, articleImageResponse2))
                .comments(List.of(commentResponse1, commentResponse2))
                .like(10L)
                .build();

        given(articleQueryService.findArticle(ArgumentMatchers.anyLong()))
                .willReturn(articleResponse);

        //when //then
        mockMvc.perform(get("/articles/{articleId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.title").value("피드 제목"))
                .andExpect(jsonPath("$.content").value("피드 내용"))
                .andExpect(jsonPath("$.articleImages.length()").value(2))
                .andExpect(jsonPath("$.articleImages[0].id").value(1))
                .andExpect(jsonPath("$.articleImages[0].imagesUrl").value("url1"))
                .andExpect(jsonPath("$.articleImages[1].id").value(2))
                .andExpect(jsonPath("$.articleImages[1].imagesUrl").value("url2"))
                .andExpect(jsonPath("$.comments.length()").value(2))
                .andExpect(jsonPath("$.comments[0].id").value(1))
                .andExpect(jsonPath("$.comments[0].username").value("dog"))
                .andExpect(jsonPath("$.comments[0].content").value("댓글1"))
                .andExpect(jsonPath("$.comments[1].id").value(2))
                .andExpect(jsonPath("$.comments[1].username").value("cat"))
                .andExpect(jsonPath("$.comments[1].content").value("댓글2"))
                .andExpect(jsonPath("$.like").value(10))
                .andExpect(status().isOk())
                .andDo(print());
    }
}