package com.homecooked.common.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Transient
    @JsonIgnore
    @ElementCollection
    private Set<GrantedAuthority> authorities = new HashSet<>();

    @Column(name = "email")
    @NotNull(message = "Invalid email is empty")
    @NotEmpty(message = "Invalid email is empty")
    @Pattern(regexp = "^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$", message = "Invalid Email Format")
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    private String fullName;

    @Column(length = 32)
    @Size(max = 32, message = "Password should be 6 to 23 length in characters")
    private String password;

    private LocalDateTime birthDate;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateLastAction;

    @Column(length = 65)
    @Size(max = 65, message = "First Name should be 2 to 30 length in characters")
    @NotNull(message = "First Name should not be empty")
    @NotEmpty(message = "First Name should not be empty")
    private String firstName;

    @Column(length = 65)
    @Size(max = 65, message = "First Name should be 2 to 30 length in characters")
    @NotNull(message = "Last Name should not be empty")
    @NotEmpty(message = "Last Name should not be empty")
    private String lastName;

    private String language;

    private boolean sex;

    public User(@NonNull String authority) {
        this.authorities.add(new SimpleGrantedAuthority(authority));
    }

    public String getRole() {
        return authorities.iterator().next().getAuthority();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.status.equals(UserStatus.ACTIVE);
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return this.status.equals(UserStatus.ACTIVE);
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return this.status.equals(UserStatus.ACTIVE);
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return this.status.equals(UserStatus.ACTIVE);
    }

    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dateLastAction = LocalDateTime.now();
    }

}
