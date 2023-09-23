package ohchangmin.sns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ohchangmin.sns.controller.request.LoginRequest;
import ohchangmin.sns.controller.request.SignUpRequest;
import ohchangmin.sns.jwt.JwtTokenFilter;
import ohchangmin.sns.jwt.JwtTokenUtils;
import ohchangmin.sns.service.AuthService;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = AuthController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = JwtTokenFilter.class
                )
        })
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean AuthService authService;
    @MockBean JwtTokenUtils jwtTokenUtils;

    @DisplayName("회원가입을 할 수 있다.")
    @Test
    void signUp() throws Exception {
        // given
        SignUpRequest request = SignUpRequest.builder().username("user").password("1234").build();

        //when //then
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("회원가입 시 유저아이디를 입력 해야한다.")
    @Test
    void signUpWithOutUsername() throws Exception {
        // given
        SignUpRequest request = SignUpRequest.builder().password("1234").build();

        //when //then
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("유저아이디를 입력해야합니다."))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("회원가입 시 패스워드를 입력 해야한다.")
    @Test
    void signUpWithOutPassword() throws Exception {
        // given
        SignUpRequest request = SignUpRequest.builder().username("user").build();

        //when //then
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("패스워드를 입력해야합니다."))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("로그인을 할 수 있다.")
    @Test
    void login() throws Exception {
        // given
        String username = "user";
        String token = "token";

        LoginRequest request = LoginRequest.builder().username(username).password("1234").build();

        given(authService.login(anyString(), anyString())).willReturn(username);
        given(jwtTokenUtils.generateToken(anyString())).willReturn(token);

        //when //then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("로그인 시 유저아이디를 입력 해야한다.")
    @Test
    void loginWithoutUsername() throws Exception {
        // given
        String token = "token";

        LoginRequest request = LoginRequest.builder().password("1234").build();

        given(authService.login(anyString(), anyString())).willReturn("user");
        given(jwtTokenUtils.generateToken(anyString())).willReturn(token);

        //when //then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("유저아이디를 입력해야합니다."))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("로그인 시 패스워드를 입력 해야한다.")
    @Test
    void loginWithoutPassword() throws Exception {
        // given
        String username = "user";
        String token = "token";

        LoginRequest request = LoginRequest.builder().username(username).build();

        given(authService.login(anyString(), anyString())).willReturn(username);
        given(jwtTokenUtils.generateToken(anyString())).willReturn(token);

        //when //then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("패스워드를 입력해야합니다."))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}