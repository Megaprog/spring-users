package users.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import users.model.User;

import java.util.Collections;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
    private final User user;

    public CurrentUser(User user) {
        super(user.getName(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return super.toString() + ": user=" + getUser() + "; ";
    }
}
