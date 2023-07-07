package org.lessons.springmyphotogallery.security;

import org.lessons.springmyphotogallery.model.Role;
import org.lessons.springmyphotogallery.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DatabaseUserDetails implements UserDetails {

    // FIELDS
    private final String username;

    private final String password;

    private final Set<GrantedAuthority> authorities;

    // costruttore che copia i dati di uno User per costruire un DatabaseUserDetails
    public DatabaseUserDetails(User user) {
        // copio i campi che hanno corrispondenza
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.authorities = new HashSet<>();
        // itero su tutti i ruoli e li trasformo in Authorities
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
    }

    // OVERRIDE METHODS
    // settare i valori degli override da ritornare (per abilitare l'utente)
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
