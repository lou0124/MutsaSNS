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
                CommentController.class,
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
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.sns.com", uriPort = 443)
@AutoConfigureMockMvc(addFilters = false)
public abstract class ControllerIntegrationTestSupport {

    protected final static String AUTH_VALUE = "Bearer $TOKEN";

    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;

    @MockBean protected AuthService authService;
    @MockBean protected ArticleService articleService;
    @MockBean protected ArticleQueryService articleQueryService;
    @MockBean protected CommentService commentService;
    @MockBean protected UserService userService;
    @MockBean protected LikeService likeService;
    @MockBean protected FollowService followService;
    @MockBean protected FriendService friendService;

    @MockBean protected JwtTokenUtils jwtTokenUtils;
    @MockBean protected FileStore fileStore;
}
