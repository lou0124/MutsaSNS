package ohchangmin.sns.service;

import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {

    private final Long id;

    public UserPrincipal(ohchangmin.sns.domain.User user) {
        super(user.getUsername(), user.getPassword(), List.of());
        id = user.getId();
    }

    public Long getId() {
        return id;
    }
}
