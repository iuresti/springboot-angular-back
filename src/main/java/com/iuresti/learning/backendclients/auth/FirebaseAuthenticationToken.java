package com.iuresti.learning.backendclients.auth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * UsernamePasswordAuthenticationToken
 *
 * @author prvoslav
 */
public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -1869548136546750302L;
    private final Object principal;
    private Object credentials;

    public FirebaseAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    /**
     * This constructor should only be used by
     * <code>AuthenticationManager</code> or <code>AuthenticationProvider</code>
     * implementations that are satisfied with producing a trusted (i.e.
     * {@link #isAuthenticated()} = <code>true</code>) authentication token.
     */
    public FirebaseAuthenticationToken(Authentication baseObject,
                                       Collection<? extends GrantedAuthority> authorities, boolean authenticated) {
        super(authorities);
        this.principal = baseObject.getPrincipal();
        this.credentials = baseObject.getCredentials();
        super.setAuthenticated(authenticated);
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }
}
