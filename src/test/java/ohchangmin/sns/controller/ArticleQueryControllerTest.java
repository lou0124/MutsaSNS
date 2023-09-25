package ohchangmin.sns.controller;

import ohchangmin.sns.controller.security.WithMockCustomUser;
import ohchangmin.sns.service.response.ArticleElement;
import ohchangmin.sns.service.response.ArticleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.MediaType;

import java.util.List;

import static ohchangmin.sns.service.response.ArticleResponse.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
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

        given(articleQueryService.findArticles(anyLong()))
                .willReturn(List.of(articleElement1, articleElement2));

        //when //then
        mockMvc.perform(get("/users/{userId}/articles", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        jsonPath("$.count").value(2),
                        jsonPath("$.data.length()").value(2),
                        jsonPath("$.data[0].id").value(1),
                        jsonPath("$.data[0].username").value("user"),
                        jsonPath("$.data[0].title").value("제목1"),
                        jsonPath("$.data[0].thumbnail").value("이미지 경로1"),
                        jsonPath("$.data[1].id").value(2),
                        jsonPath("$.data[1].username").value("user"),
                        jsonPath("$.data[1].title").value("제목2"),
                        jsonPath("$.data[1].thumbnail").value("이미지 경로2")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("my-articles-find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
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

        given(articleQueryService.findArticle(anyLong()))
                .willReturn(articleResponse);

        //when //then
        mockMvc.perform(get("/articles/{articleId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        jsonPath("$.id").value(1),
                        jsonPath("$.username").value("user"),
                        jsonPath("$.title").value("피드 제목"),
                        jsonPath("$.content").value("피드 내용"),
                        jsonPath("$.articleImages.length()").value(2),
                        jsonPath("$.articleImages[0].id").value(1),
                        jsonPath("$.articleImages[0].imagesUrl").value("url1"),
                        jsonPath("$.articleImages[1].id").value(2),
                        jsonPath("$.articleImages[1].imagesUrl").value("url2"),
                        jsonPath("$.comments.length()").value(2),
                        jsonPath("$.comments[0].id").value(1),
                        jsonPath("$.comments[0].username").value("dog"),
                        jsonPath("$.comments[0].content").value("댓글1"),
                        jsonPath("$.comments[1].id").value(2),
                        jsonPath("$.comments[1].username").value("cat"),
                        jsonPath("$.comments[1].content").value("댓글2"),
                        jsonPath("$.like").value(10)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("article-find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @WithMockCustomUser
    @DisplayName("팔로워의 피드 목록을 조회할 수 있다.")
    @Test
    void findFollowArticles() throws Exception {
        //given
        ArticleElement articleElement1 = ArticleElement.builder().id(1L).username("user").title("제목1").thumbnail("이미지 경로1").build();
        ArticleElement articleElement2 = ArticleElement.builder().id(2L).username("user").title("제목2").thumbnail("이미지 경로2").build();

        given(articleQueryService.findFollowersArticles(anyLong()))
                .willReturn(List.of(articleElement1, articleElement2));

        //when //then
        mockMvc.perform(get("/articles/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        jsonPath("$.count").value(2),
                        jsonPath("$.data.length()").value(2),
                        jsonPath("$.data[0].id").value(1),
                        jsonPath("$.data[0].username").value("user"),
                        jsonPath("$.data[0].title").value("제목1"),
                        jsonPath("$.data[0].thumbnail").value("이미지 경로1"),
                        jsonPath("$.data[1].id").value(2),
                        jsonPath("$.data[1].username").value("user"),
                        jsonPath("$.data[1].title").value("제목2"),
                        jsonPath("$.data[1].thumbnail").value("이미지 경로2")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("followers-articles-find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @WithMockCustomUser
    @DisplayName("친구의 피드 목록을 조회할 수 있다.")
    @Test
    void findFriendsArticles() throws Exception {
        //given
        ArticleElement articleElement1 = ArticleElement.builder().id(1L).username("user").title("제목1").thumbnail("이미지 경로1").build();
        ArticleElement articleElement2 = ArticleElement.builder().id(2L).username("user").title("제목2").thumbnail("이미지 경로2").build();

        given(articleQueryService.findFriendsArticles(anyLong()))
                .willReturn(List.of(articleElement1, articleElement2));

        //when //then
        mockMvc.perform(get("/articles/friends")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        jsonPath("$.count").value(2),
                        jsonPath("$.data.length()").value(2),
                        jsonPath("$.data[0].id").value(1),
                        jsonPath("$.data[0].username").value("user"),
                        jsonPath("$.data[0].title").value("제목1"),
                        jsonPath("$.data[0].thumbnail").value("이미지 경로1"),
                        jsonPath("$.data[1].id").value(2),
                        jsonPath("$.data[1].username").value("user"),
                        jsonPath("$.data[1].title").value("제목2"),
                        jsonPath("$.data[1].thumbnail").value("이미지 경로2")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("friends-articles-find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

}