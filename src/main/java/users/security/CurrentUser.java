package users.security;

import org.springframework.security.core.authority.AuthorityUtils;
import users.model.User;
import users.model.UserRole;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
    private final long id;
    private final String name;
    private final String email;
    private final UserRole role;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return super.toString() + ": id=" + getId() + ": name=" + getName() + ": role=" + getRole() + "; ";
    }
}
