package ua.com.alevel.plannerbox.rest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.com.alevel.plannerbox.config.SecurityConfig;

import java.util.List;

@TestConfiguration
@Order(1)
public class NoAuthTestConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .apply(SecurityConfig.CommonSecurityConfiguration.commonSecurityConfiguration()).and()
                .addFilterBefore((servletRequest, servletResponse, filterChain) -> {
                            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER"));
                            User principal = new User("admin", "adminPlanner", authorities);
                            SecurityContextHolder.getContext()
                                    .setAuthentication(new UsernamePasswordAuthenticationToken(principal, principal.getPassword()));
                            filterChain.doFilter(servletRequest, servletResponse);
                        },
                        UsernamePasswordAuthenticationFilter.class);
    }
}
