package com.pos.app.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("com.pos.app")
@EnableScheduling
@PropertySources({
        @PropertySource(value = "file:./pos_app.properties", ignoreResourceNotFound = false)
})
public class SpringConfig {
}