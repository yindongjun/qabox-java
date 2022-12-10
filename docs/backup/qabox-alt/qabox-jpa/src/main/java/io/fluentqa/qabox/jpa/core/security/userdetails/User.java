package io.fluentqa.qabox.jpa.core.security.userdetails;

import java.util.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;

/**
 * A generic User Detail implementation inspired by Spring Security.
 */
@Data
@NoArgsConstructor
public class User implements UserDetails {

    @NonNull
    private String username;
    @NonNull
    private String password;
    boolean enabled;
    boolean accountNonExpired;
    boolean credentialsNonExpired;
    boolean accountNonLocked;
    @NonNull
    Collection<GrantedAuthority> authorities;

    /**
     * Construct the <code>User</code> with the details required.
     *
     * @param username              the username presented the authentication provider.
     * @param password              the password that should be presented to the authentication provider
     * @param enabled               set to <code>true</code> if the user is enabled
     * @param accountNonExpired     set to <code>true</code> if the account has not expired
     * @param credentialsNonExpired set to <code>true</code> if the credentials have not expired
     * @param accountNonLocked      set to <code>true</code> if the account is not locked
     * @param authorities           the authorities that should be granted to the caller if they presented the
     *                              correct username and password and the user is enabled. Not null.
     * @throws IllegalArgumentException if a <code>null</code> value was passed either as a
     *                                  parameter or as an element in the <code>GrantedAuthority</code> collection
     */
    public User(@NonNull String username, @NonNull String password, boolean enabled,
                boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                @NonNull Collection<GrantedAuthority> authorities) {
        Assertions.assertTrue(username != null && !"".equals(username) && password != null,
                "Cannot pass null or empty values to constructor");
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }

    private static Set<GrantedAuthority> sortAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        Objects.nonNull(authorities);
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        Set<GrantedAuthority> sortedAuthorities = new HashSet<>();
        for (GrantedAuthority grantedAuthority : authorities) {
            Objects.nonNull(grantedAuthority
            );
            sortedAuthorities.add(grantedAuthority);
        }
        return sortedAuthorities;
    }

}
