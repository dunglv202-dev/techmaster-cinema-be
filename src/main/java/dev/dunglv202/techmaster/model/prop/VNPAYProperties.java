package dev.dunglv202.techmaster.model.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "payment.vn-pay")
public class VNPAYProperties {
    private String version = "2.1.0";
    private String queryUrl;
    private String code;
    private String secret;
    private String url;
    private String callback;
}
