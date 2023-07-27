package com.example.project5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.example.project5.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

// 컨트롤러 메서드들에서 @PreAuthorize, @PostAuthorize 애너테이션을 이용할 수 있도록.
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration // 스프링 부트 앱 환경설정(configuration)을 자바 코드로 설정
@RequiredArgsConstructor
@EnableWebSecurity

public class SecurityConfig { 
    
    private final AuthenticationFailureHandler customFailureHandler;
    
	
    @Bean // 스프링 컨텍스트에서 생성, 관리하는 객체 - 필요한 곳에 의존성 주입
    // 암호화(복호화) 알고리즘 객체
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
         
        http.formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/login_home")
        .failureHandler(customFailureHandler)
        .defaultSuccessUrl("/")
        .and()
        .logout()
        .logoutSuccessUrl("/")
        .invalidateHttpSession(true).deleteCookies("JSESSIONID");
     
        return http.build();
    }
    
}
