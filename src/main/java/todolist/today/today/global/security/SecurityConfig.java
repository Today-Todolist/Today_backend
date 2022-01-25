package todolist.today.today.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import todolist.today.today.global.security.filter.FilterConfig;
import todolist.today.today.global.security.path.ApiPath;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RequestBucketProvider requestBucketProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().disable()
                .formLogin().disable();

        for(String path : ApiPath.getCertifyPath(HttpMethod.GET))
            http.authorizeRequests().antMatchers(HttpMethod.GET, path);

        for(String path : ApiPath.getCertifyPath(HttpMethod.POST))
            http.authorizeRequests().antMatchers(HttpMethod.POST, path);

        for(String path : ApiPath.getCertifyPath(HttpMethod.PATCH))
            http.authorizeRequests().antMatchers(HttpMethod.PATCH, path);

        for(String path : ApiPath.getCertifyPath(HttpMethod.PUT))
            http.authorizeRequests().antMatchers(HttpMethod.PUT, path);

        for(String path : ApiPath.getCertifyPath(HttpMethod.DELETE))
            http.authorizeRequests().antMatchers(HttpMethod.DELETE, path);

        http.authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .apply(new FilterConfig(jwtTokenProvider, requestBucketProvider, objectMapper));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}