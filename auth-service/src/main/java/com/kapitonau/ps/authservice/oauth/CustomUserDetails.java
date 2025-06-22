package com.kapitonau.ps.authservice.oauth;

import com.kapitonau.ps.authservice.model.RoleModel;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {

    @Getter
    private Long userId;
    private String email;
    @Getter
    private String username;
    private String password;
    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private boolean isActive;
    @Getter
    private List<RoleModel> roles;
    @Setter
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
