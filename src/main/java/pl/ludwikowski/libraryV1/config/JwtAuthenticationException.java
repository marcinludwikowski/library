package pl.ludwikowski.libraryV1.config;

import org.springframework.security.core.AuthenticationException;

class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
