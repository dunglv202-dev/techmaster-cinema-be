package dev.dunglv202.techmaster.model.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {
    private Duration tokenLifetime;
    private Duration refreshLifetime;

    public void setTokenLifetime(int tokenLifetime) {
        this.tokenLifetime = Duration.ofMinutes(tokenLifetime);
    }

    public void setRefreshLifetime(int refreshLifetime) {
        this.refreshLifetime = Duration.ofMinutes(refreshLifetime);
    }
}
