package io.fluentqa.qabox.jpa.core.config;

import io.fluentqa.qabox.jpa.core.mvc.controller.Controller;
import io.fluentqa.qabox.jpa.core.security.auth.SecurityContext;

/**
 * Generic Web Configuration implementation.
 * 
 */
public abstract class WebSecurityConfig extends SimpleConfiguration {

    /**
     * Sets the SecurityContext for authentication/authorization.
     * 
     * @param http the HttpSecurity context holder
     */
    protected void setSecurityContext(SecurityContext http) {
        Controller.setSecurityContext(http);
    }

    /**
     * This method will configure a new security context: it must call the
     * <code>setSecurityContext</code> method.
     */
    public abstract void configure();

}
