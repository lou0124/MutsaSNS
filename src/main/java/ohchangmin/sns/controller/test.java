package ohchangmin.sns.controller;

import ohchangmin.sns.service.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @GetMapping("/test")
    public void test(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        System.out.println("userPrincipal.getId() = " + userPrincipal.getId());
    }
}
