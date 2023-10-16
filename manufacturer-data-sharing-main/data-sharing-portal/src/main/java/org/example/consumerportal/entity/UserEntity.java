package org.example.consumerportal.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "producers")
@Data
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "producer_id")
    @SequenceGenerator(name="identifier", sequenceName="producers_producer_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    private Integer producerId;

    private String username;

    private String firstName;
    private String surname;
    private String email;

    @Column(name = "token_id")
    private String password;

    private boolean enabled;
    @Column(name = "token_creation_date_time")
    private LocalDateTime tokenCreationDateTime;
    @Column(name = "account_non_expired")
    private boolean accountNonExpired;
    @Column(name = "account_non_locked")
    private boolean accountNonLocked;
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "read");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
