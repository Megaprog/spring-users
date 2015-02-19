package users.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
    private Timestamp created;

    @Column(nullable = false)
    @Version
    private Timestamp updated;

    @Column(nullable = false)
    private String name = "";

    @Column(nullable = false)
    private String password = "";

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private UserRole role = UserRole.User;

    private transient ZoneOffset zoneOffset = ZonedDateTime.now(ZoneId.systemDefault()).getOffset();
    private int time_zone = zoneOffset.getTotalSeconds();

    @ElementCollection
    @OrderColumn(name = "address_order")
    @Column(name = "address", nullable = false, length = 512, unique = true)
    @CollectionTable(name = "address", joinColumns = @JoinColumn(name = "user_id"), foreignKey = @ForeignKey(name = "address_to_user"))
    private List<String> addresses = new ArrayList<>();

    public User() {
    }

    public User(String name, String password, String email, UserRole role, String address) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.addresses.add(address);
    }

    public long getId() {
        return id;
    }

    public OffsetDateTime getCreatedTime() {
        return OffsetDateTime.of(created.toLocalDateTime(), getZoneOffset());
    }

    public OffsetDateTime getUpdatedTime() {
        return OffsetDateTime.of(updated.toLocalDateTime(), getZoneOffset());
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

    public int getTimeZone() {
        return time_zone;
    }

    public void setTimeZone(int time_zone) {
        this.time_zone = time_zone;
        this.zoneOffset = ZoneOffset.ofTotalSeconds(time_zone);
    }

    public ZoneOffset getZoneOffset() {
        return zoneOffset;
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
                ", created=" + getCreatedTime() +
                ", updated=" + getUpdatedTime() +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", timeZone=" + getZoneOffset() +
                ", addresses=" + addresses +
                '}';
    }

    @PrePersist
    private void prePersist() {
        created = Timestamp.from(Instant.now());
    }

    @PostLoad
    private void postLoad() {
        setTimeZone(time_zone);
    }
}
