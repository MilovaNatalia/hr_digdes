package com.digdes.config;

import com.digdes.models.Role;
import com.digdes.services.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableConfigurationProperties
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SecurityUserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/departments/get/**", "/departments/all", "/employees/all", "/employees/get/**")
                    .hasAnyRole(Role.ADMIN, Role.MODERATOR, Role.USER)
                //todo: Moderator role?
                .and().authorizeRequests().antMatchers("/departments/update/", "/employees/update")
                    .hasAnyRole(Role.MODERATOR, Role.ADMIN)
                .and().authorizeRequests().antMatchers("/**")
                    .hasAnyRole(Role.ADMIN)
                .and().httpBasic()
                .and().authorizeRequests().anyRequest().denyAll()
                .and().sessionManagement().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder)
            throws Exception {
        builder.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
