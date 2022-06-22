package com.jpmarket.config.auth.requestAndResponse;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.bytebuddy.implementation.EqualsMethod;
import net.bytebuddy.implementation.HashCodeMethod;
import net.bytebuddy.implementation.ToStringMethod;

import java.io.Serializable;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
public class JwtAuthResponse implements Serializable {
    private static final long serialVersionUID = 129221684671752687L;

    private final String token;

    @Override
    public boolean equals(Object o) {
        return EqualsMethod.isolated().equals(o);
    }

    @Override
    public int hashCode() {
        return HashCodeMethod.usingDefaultOffset().hashCode();
    }

    @Override
    public String toString() {
        return ToStringMethod.prefixedBySimpleClassName().toString();
    }

    public JwtAuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
