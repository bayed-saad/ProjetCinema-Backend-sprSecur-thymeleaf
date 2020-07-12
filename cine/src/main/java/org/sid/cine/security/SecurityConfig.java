package org.sid.cine.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();
        System.out.println("****************");
        System.out.println(passwordEncoder.encode("123456"));
        System.out.println("****************");
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select username as principal  , password as credentials , active from users where username = ?")
         .authoritiesByUsernameQuery("select username as principal , role as role from users_role where username = ?")
                .passwordEncoder(passwordEncoder).rolePrefix("ROLE_");

       
    }
    protected void configure(HttpSecurity http) throws Exception {
     // http.formLogin().loginPage("/login");
       http.formLogin();
        http.authorizeRequests().antMatchers("/delete**/**","/edit**/**","/form**/**","/save**/**","/cinema/**","/film/**","/projection/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/Home/**").hasRole("USER");
       // http.authorizeRequests().antMatchers("/admin","/login","/webjars/**").permitAll();
        // http.authorizeRequests().anyRequest().authenticated(); //every resource need authentication

     //   http.csrf();
       http.exceptionHandling().accessDeniedPage("/eror");
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
