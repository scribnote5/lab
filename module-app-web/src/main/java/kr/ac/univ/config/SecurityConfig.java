package kr.ac.univ.config;

import kr.ac.univ.handler.CustomAuthenticationEntryPoint;
import kr.ac.univ.handler.CustomAuthenticationFailureHandler;
import kr.ac.univ.handler.CustomAuthenticationSuccessHandler;
import kr.ac.univ.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/imgages/**", "/summernote/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/user/list").hasAuthority("root")
                .antMatchers("/**/form").hasAnyAuthority("root, manager, general")
                .antMatchers("/h2-console/**").permitAll() // h2-console 접근 허용
                .antMatchers("/**").permitAll()
                .and()
                .csrf().ignoringAntMatchers("/console/**") // h2-console csrf 제외
                .and()
                .headers().addHeaderWriter(new XFrameOptionsHeaderWriter(new WhiteListedAllowFromStrategy(Arrays.asList("localhost")))) // he-console X-Frame-Options 제외
                .frameOptions().sameOrigin()
                .and()
                // 로그인 설정
                .formLogin()
                .loginPage("/user/login")   // login 페이지 URL
                .loginProcessingUrl("/user/login/process")  // login 수행 URL
                // 사용자 정의 handler
                .successHandler(CustomAuthenticationSuccessHandler())
                .failureHandler(CustomAuthenticationFailureHandler())
                .defaultSuccessUrl("/user/index")   // login 성공 URL
                .permitAll()
                .and()
                // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/logout/success")
                .invalidateHttpSession(true)
                .and()
                // 예외처리
                .exceptionHandling()
                .accessDeniedPage("/user/permission-denied") // 권한이 없는 경우, 403 예외처리
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()); // 로그인하지 않은(비인증) 사용자가 접근하는 경우, 401 에러처리
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 비밀번호 암호화에 사용될 PasswordEncoder를 BCryptPasswordEncoder로 사용
     * BCryptPasswordEncoder는 해시 뿐만 아니라 Salt를 넣는 작업을 수행하므로, 입력 값이 같음에도 매번 다른 encoded된 값을 반환함
     *
     * @return
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    /**
     * 로그인 성공 후 수행하는 handler
     *
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler CustomAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    /**
     * 로그인 실패 후 수행하는 handler
     *
     * @return
     */
    @Bean
    public AuthenticationFailureHandler CustomAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    /**
     * 개발자가 원하는 시점에 로그인을 할 수 있도록 구현 가능
     *
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}