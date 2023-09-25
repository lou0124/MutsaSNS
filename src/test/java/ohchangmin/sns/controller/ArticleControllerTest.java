package ohchangmin.sns.controller;

import ohchangmin.sns.controller.request.ArticleCreateRequest;
import ohchangmin.sns.controller.security.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                        .header("Authorization", "Bearer " + "your encoded token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("article-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @WithMockCustomUser
    @DisplayName("피드 등록 시 제목을 입력해야한다.")
    @Test
    void createArticleWithoutTitle() throws Exception {
        // given
        ArticleCreateRequest request = ArticleCreateRequest.builder().title("제목 입니다.").build();

        //when //then
        mockMvc.perform(post("/articles")
                        .header("Authorization", AUTH_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("피드 내용이 입력 되어야 합니다."))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @WithMockCustomUser
    @DisplayName("피드 등록 시 내용을 입력해야한다.")
    @Test
    void createArticleWithoutContent() throws Exception {
        // given
        ArticleCreateRequest request = ArticleCreateRequest.builder().content("내용 입니다.").build();

        //when //then
        mockMvc.perform(post("/articles")
                        .header("Authorization", AUTH_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("피드 제목이 입력 되어야 합니다."))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @WithMockCustomUser
    @DisplayName("피드에 이미지를 입력할 수 있다.")
    @Test
    void addImage() throws Exception {
        // given
        MockMultipartFile image1 = new MockMultipartFile("images", "image1.jpg", "image/jpeg", "Some image1".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("images", "image2.jpg", "image/jpeg", "Some image2".getBytes());

        //when //then
        mockMvc.perform(multipart("/articles/{articleId}/article-images", 1L)
                        .file(image1)
                        .file(image2)
                        .header("Authorization", AUTH_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockCustomUser
    @DisplayName("피드에 이미지 추가 시 파일을 꼭 추가해야 한다.")
    @Test
    void addImageWithoutImages() throws Exception {
        //when //then
        mockMvc.perform(multipart("/articles/{articleId}/article-images", 1L)
                        .header("Authorization", AUTH_VALUE)
                )
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("파일을 추가해야합니다."))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @WithMockCustomUser
    @DisplayName("피드를 삭제할 수 있다.")
    @Test
    void deleteArticle() throws Exception {
        //when //then
        mockMvc.perform(delete("/articles/{articleId}", 1L)
                        .header("Authorization", AUTH_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockCustomUser
    @DisplayName("피드 이미지를 삭제할 수 있다.")
    @Test
    void deleteImage() throws Exception {
        //when //then
        mockMvc.perform(delete("/article-images/{articleImageId}", 1L)
                        .header("Authorization", AUTH_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}