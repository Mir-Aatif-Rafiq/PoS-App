package com.pos.app.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan("com.pos.app")
@PropertySources({
        @PropertySource(value = "file:./pos_app.properties", ignoreResourceNotFound = true)
})
public class SpringConfig {
}