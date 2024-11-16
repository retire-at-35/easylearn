package com.easylearn.config;

import com.easylearn.service.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class  SecurityConfig {
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Bean
    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(myUserDetailsService);
        dao.setPasswordEncoder(bCryptPasswordEncoder());

        //实现类
        ProviderManager providerManager = new ProviderManager(dao);
        return providerManager;
    }


    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //授权
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            //所以有指定的请求，包括其子路径可以访问
            try {
                http.authorizeRequests()
                        .antMatchers("/pub/**", "/images/**").permitAll()
                        .antMatchers("/user/**").hasAnyRole("user", "admin")
                        .antMatchers("/admin/**").hasRole("admin")
                        .anyRequest().authenticated();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });

        //允许前端post方式提交
        //跨站请求伪造（ Cross-site request forgery）
        http.csrf(c -> c.disable());

        //返回构建的对象
        return http.build();
    }
}
