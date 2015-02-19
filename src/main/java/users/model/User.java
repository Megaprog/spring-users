package users.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = {
        @Index(name = "by_name", columnList = "name"),
        @Index(name = "by_email", columnList = "email", unique = true)
})
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private Timestamp created;

    @Column(nullable = false)
    @Version
    private Timestamp updated;

    @PrePersist
    private void prePersist() {
        created = Timestamp.from(Instant.now());
    }

    @Column(nullable = false)
    private String name = "";

    @Column(nullable = false)
    private String password = "";

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private UserRole role = UserRole.User;

    @ElementCollection
    @OrderColumn(name = "address_order")
    @Column(name = "address", nullable = false, length = 512, unique = true)
    @CollectionTable(name = "address", joinColumns = @JoinColumn(name = "user_id"), foreignKey = @ForeignKey(name = "address_to_user"))
    private List<String> addresses = new ArrayList<>();

    public User() {
    }

    public User(String name, String password, String email, UserRole role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", addresses=" + addresses +
                '}';
    }
}
