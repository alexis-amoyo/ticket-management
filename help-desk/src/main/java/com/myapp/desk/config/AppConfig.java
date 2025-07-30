package com.myapp.desk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration

public class AppConfig  {

/*
    @Bean
    UserDetailsService userDetailsService(){
        UserDetails user1 =
                User.withUsername("admin").password("{noop}admin").roles("ADMIN").build();
        UserDetails user2 =
                User.withUsername("guest").password("{noop}admin").roles("GUEST").build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers("/tickets/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }
*/
}
