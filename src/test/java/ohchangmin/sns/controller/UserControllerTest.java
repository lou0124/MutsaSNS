package ohchangmin.sns.controller;

import ohchangmin.sns.controller.security.WithMockCustomUser;
import ohchangmin.sns.service.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.mock.web.MockMultipartFile;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerIntegrationTestSupport {

    @WithMockCustomUser
    @DisplayName("유저의 프로필 이미지를 수정할 수 있다.")
    @Test
    void changeImage() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "Some image".getBytes());

        //when //then
        mockMvc.perform(multipart(("/change-profile"))
                        .file(image)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-profile-change",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @WithMockCustomUser
    @DisplayName("유저를 조회할 수 있다.")
    @Test
    void findUser() throws Exception {
        //given
        BDDMockito.given(userService.findUser(anyLong()))
                        .willReturn(
                                UserResponse.builder()
                                        .username("user")
                                        .profileImage("imageUrl")
                                        .build()
                        );

        //when //then
        mockMvc.perform(get(("/users/{userId}"), 1L))
                .andExpectAll(
                        jsonPath("$.username").value("user"),
                        jsonPath("$.profileImage").value("imageUrl")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}