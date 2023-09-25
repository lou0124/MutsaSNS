package ohchangmin.sns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ohchangmin.sns.file.FileStore;
import ohchangmin.sns.jwt.JwtTokenFilter;
import ohchangmin.sns.jwt.JwtTokenUtils;
import ohchangmin.sns.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
        controllers = {
                AuthController.class,
                ArticleController.class,
                ArticleQueryController.class,
                UserController.class,
                LikeController.class,
                FollowController.class,
                FriendController.class
        },
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = JwtTokenFilter.class
                )
        })
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
public abstract class ControllerIntegrationTestSupport {

    protected final static String AUTH_VALUE = "Bearer $TOKEN";

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean AuthService authService;
    @MockBean ArticleService articleService;
    @MockBean ArticleQueryService articleQueryService;
    @MockBean UserService userService;
    @MockBean LikeService likeService;
    @MockBean FollowService followService;
    @MockBean FriendService friendService;

    @MockBean JwtTokenUtils jwtTokenUtils;
    @MockBean FileStore fileStore;
}
