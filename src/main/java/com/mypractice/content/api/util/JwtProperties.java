package com.mypractice.content.api.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

    private String secretKey;
    private long validityInMs = 3600000; // 1h

}