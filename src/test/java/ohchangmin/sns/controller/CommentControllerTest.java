package ohchangmin.sns.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ohchangmin.sns.controller.request.CommentCreateRequest;
import ohchangmin.sns.controller.request.CommentUpdateRequest;
import ohchangmin.sns.controller.security.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentControllerTest extends ControllerIntegrationTestSupport {

    @WithMockCustomUser
    @DisplayName("댓글을 등록할 수 있다.")
    @Test
    void addComment() throws Exception {
        //when //then
        mockMvc.perform(post(("/articles/{articleId}/comments"), 1L)
                        .header("Authorization", AUTH_VALUE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommentCreateRequest("댓글 내용")))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("comment-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @WithMockCustomUser
    @DisplayName("댓글 등록 시 내용을 입력해야한다.")
    @Test
    void addCommentWithoutContent() throws Exception {
        //when //then
        mockMvc.perform(post(("/articles/{articleId}/comments"), 1L)
                        .header("Authorization", AUTH_VALUE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommentCreateRequest()))
                )
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("댓글 내용이 입력 되어야 합니다."))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @WithMockCustomUser
    @DisplayName("댓글을 수정할 수 있다.")
    @Test
    void modifyComment() throws Exception {
        //when //then
        mockMvc.perform(post(("/comments/{commentId}"), 1L)
                        .header("Authorization", AUTH_VALUE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommentUpdateRequest("댓글 내용")))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("comment-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @WithMockCustomUser
    @DisplayName("댓글 수정시 내용을 입력해야한다.")
    @Test
    void modifyCommentWithoutContent() throws Exception {
        //when //then
        mockMvc.perform(post(("/comments/{commentId}"), 1L)
                        .header("Authorization", AUTH_VALUE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommentUpdateRequest()))
                )
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("댓글 내용이 입력 되어야 합니다."))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @WithMockCustomUser
    @DisplayName("댓글을 삭제할 수 있다.")
    @Test
    void deleteComment() throws Exception {
        //when //then
        mockMvc.perform(delete(("/comments/{commentId}"), 1L)
                        .header("Authorization", AUTH_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("comment-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}