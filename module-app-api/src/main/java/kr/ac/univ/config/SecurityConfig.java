package kr.ac.univ.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${module-app-web.address}")
    private String moduleAppWebAddress;
    @Value("${module-app-admin.address}")
    private String moduleAppAdminAddress;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // iframe 동일 도메인 접근 허용
        // http.headers().frameOptions().disable();

        http.httpBasic().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and().csrf().disable()

                .authorizeRequests()
                .anyRequest().permitAll();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin(moduleAppAdminAddress);
        configuration.addAllowedOrigin(moduleAppWebAddress);
        // configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}