package ua.com.alevel.plannerbox.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.com.alevel.plannerbox.security.jwt.JwtTokenFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenFilter jwtTokenFilter;

    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private static final String USER_CONTROLLER = "/api/v1/users";
    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER = "USER";

    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.apply(CommonSecurityConfiguration.commonSecurityConfiguration())
//            .httpBasic().disable()
//            .csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .authorizeRequests()
//            .antMatchers(HttpMethod.POST, LOGIN_ENDPOINT).permitAll()
//            .antMatchers(HttpMethod.POST, USER_CONTROLLER).permitAll()
//            .antMatchers(USER_CONTROLLER + "*").permitAll()
//            .antMatchers(ADMIN_ENDPOINT).hasRole(ADMIN_ROLE)
//            .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    public static class CommonSecurityConfiguration extends AbstractHttpConfigurer<CommonSecurityConfiguration, HttpSecurity> {

        @Override
        public void init(HttpSecurity http) throws Exception {
            http
                    .httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, LOGIN_ENDPOINT).permitAll()
                    .antMatchers(HttpMethod.POST, USER_CONTROLLER).permitAll()
                    .antMatchers(HttpMethod.PUT, USER_CONTROLLER).hasRole(ADMIN_ROLE)
                    .antMatchers(USER_CONTROLLER + "*").permitAll()
                    .antMatchers(ADMIN_ENDPOINT).hasRole(ADMIN_ROLE)
                    .anyRequest().authenticated();
        }

        public static CommonSecurityConfiguration commonSecurityConfiguration() {
            return new CommonSecurityConfiguration();
        }
    }
}
