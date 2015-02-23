package users.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    private Timestamp created = Timestamp.from(Instant.now());

    @Column(nullable = false)
    @Version
    private Timestamp updated = created;

    @Column(nullable = false)
    private String name = "";

    @Column(nullable = false)
    private String password = "";

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private UserRole role = UserRole.User;

    @Column(nullable = false)
    private String time_zone = ZoneId.systemDefault().getId();

    @ElementCollection
    @OrderColumn(name = "address_order")
    @CollectionTable(name = "address", joinColumns = @JoinColumn(name = "user_id"), foreignKey = @ForeignKey(name = "address_to_user"))
    private List<Address> addresses = new ArrayList<>();

    public User() {
    }

    public User(String name, String password, String email, UserRole role, List<Address> addresses) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.addresses = addresses;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    @DateTimeFormat(style = "LL")
    public ZonedDateTime getCreatedZoneTime() {
        return ZonedDateTime.of(created.toLocalDateTime(), ZoneId.of(time_zone));
    }

    @DateTimeFormat(style = "LL")
    public ZonedDateTime getUpdatedZoneTime() {
        return ZonedDateTime.of(updated.toLocalDateTime(), ZoneId.of(time_zone));
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

    public String getTimeZone() {
        return time_zone;
    }

    public void setTimeZone(String time_zone) {
        this.time_zone = time_zone;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", created=" + getCreatedZoneTime() +
                ", updated=" + getUpdatedZoneTime() +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", timeZone=" + time_zone +
                ", addresses=" + addresses +
                '}';
    }

    @PrePersist
    private void prePersist() {
        created = Timestamp.from(Instant.now());
    }
}
