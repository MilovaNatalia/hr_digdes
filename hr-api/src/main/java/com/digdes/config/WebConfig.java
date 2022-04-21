package com.digdes.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Import({HibernateConfig.class})
@EnableWebMvc
@ComponentScan(basePackages = {"com.digdes"})
@Configuration
public class WebConfig {
}
