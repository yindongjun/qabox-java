package io.fluentqa.qabox.jpa.config;

import io.fluentqa.qabox.jpa.core.config.Configuration;
import io.fluentqa.qabox.jpa.core.config.WebSecurityConfig;
import io.fluentqa.qabox.jpa.core.security.crypto.BCryptPasswordEncoder;
import io.fluentqa.qabox.jpa.core.security.crypto.PasswordEncoder;
import io.fluentqa.qabox.jpa.core.security.web.HttpSecurity;
import io.fluentqa.qabox.jpa.security.auth.SimpleAuthenticationProvider;
import io.fluentqa.qabox.jpa.security.auth.SimpleAuthorizationProvider;
import io.fluentqa.qabox.jpa.security.filter.SessionJwtAuthorizationFilter;
import io.fluentqa.qabox.jpa.security.services.SimpleUserService;
import lombok.Getter;
import lombok.Setter;

/**
 * A Security Configuration.
 * Session JWT as default implemantion
 */
@Getter
@Setter
@Configuration
public final class SecurityConfig extends WebSecurityConfig {

    @Override
    public void configure() {

        HttpSecurity http = new HttpSecurity(authenticationProvider(), authorizationProvider());

        SessionJwtAuthorizationFilter jwtAuthorizationFilter = new SessionJwtAuthorizationFilter();
        jwtAuthorizationFilter.setAuthorizationManager(http.getAuthorizationManager());

        http.cors().authorizationFilter(jwtAuthorizationFilter).build();

        setSecurityContext(http);
    }

    /**
     * Obtains the used password-encoder.
     * 
     * @return the used password-encoder
     */
    public static PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Generates an Authentication Provider.
     * 
     * @return a Simple Authentication Provider with the current <code>PasswordEncoder</code> and
     *         <code>UserDetailsService</code>
     */
    public static SimpleAuthenticationProvider authenticationProvider() {
        SimpleAuthenticationProvider authProvider = new SimpleAuthenticationProvider();
        authProvider.setPasswordEncoder(getPasswordEncoder());
        authProvider.setUserDetailsService(new SimpleUserService());
        return authProvider;
    }


    /**
     * Builds an Authorization Provider.
     * 
     * @return a Simple <code>AuthorizationProvider</code>.
     */
    public static SimpleAuthorizationProvider authorizationProvider() {
        return new SimpleAuthorizationProvider();
    }

}
