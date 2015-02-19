package users.security;

import org.springframework.security.core.authority.AuthorityUtils;
import users.model.UserRole;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
    private final long id;
    private final String name;
    private final String email;
    private final UserRole role;

    public CurrentUser(String email, String password, String name, UserRole role, long id) {
        super(email, password, AuthorityUtils.createAuthorityList(role.name()));
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
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
