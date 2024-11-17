package com.easylearn.config;

import com.easylearn.service.impl.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    public SecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(myUserDetailsService);
        dao.setPasswordEncoder(bCryptPasswordEncoder());
        return new ProviderManager(dao);
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
            auth.requestMatchers("/pub/**", "images/**").permitAll()
                    .requestMatchers("/common/**").hasAnyRole("superadmin", "admin")
                    .requestMatchers("/super/**").hasRole("superadmin")
                    .anyRequest().authenticated();//任何其他请求都需要验证
        });

        //允许前端post方式提交
        //跨站请求伪造（ Cross-site request forgery）
        http.csrf(c -> c.disable());
//        http.cors(withDefaults());
        //返回构建的对象
        return http.build();
    }

}
