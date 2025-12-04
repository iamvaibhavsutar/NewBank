package com.project.NewBank.Service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;

public class UserDetailsImpl implements org.springframework.security.core.userdetails.UserDetails {

    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    

    public UserDetailsImpl(String username, String email,String password,
        Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
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
