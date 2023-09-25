package ohchangmin.sns.controller;

import ohchangmin.sns.controller.security.WithMockCustomUser;
import ohchangmin.sns.service.response.FriendRequestElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FriendControllerTest extends ControllerIntegrationTestSupport {

    @WithMockCustomUser
    @DisplayName("친구 요청을 할 수 있다.")
    @Test
    void requestFriend() throws Exception {
        //when //then
        mockMvc.perform(post(("/users/{userId}/friend-requests"), 1L)
                        .header("Authorization", AUTH_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("friend-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @WithMockCustomUser
    @DisplayName("친구 요청 목록을 조회 할 수 있다.")
    @Test
    void findRequestFriend() throws Exception {
        //given
        given(friendService.findRequestFriends(anyLong()))
                .willReturn(List.of(
                        FriendRequestElement.builder()
                                .id(1L)
                                .username("user1")
                                .build(),
                        FriendRequestElement.builder()
                                .id(2L)
                                .username("user2")
                                .build()
                ));

        //when //then
        mockMvc.perform(get(("/friend-requests"))
                        .header("Authorization", AUTH_VALUE)
                )
                .andExpectAll(
                        jsonPath("$.count").value(2),
                        jsonPath("$.data[0].id").value(1),
                        jsonPath("$.data[0].username").value("user1"),
                        jsonPath("$.data[1].id").value(2),
                        jsonPath("$.data[1].username").value("user2")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("friend-request-find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @WithMockCustomUser
    @DisplayName("친구 요청을 수락 할 수 있다.")
    @Test
    void acceptFriend() throws Exception {
        //when //then
        mockMvc.perform(post(("/friend-requests/{requestId}/accept"), 1L)
                        .header("Authorization", AUTH_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("friend-request-accept",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @WithMockCustomUser
    @DisplayName("친구 요청을 거절 할 수 있다.")
    @Test
    void rejectFriend() throws Exception {
        //when //then
        mockMvc.perform(post(("/friend-requests/{requestId}/reject"), 1L)
                        .header("Authorization", AUTH_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("friend-request-reject",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}