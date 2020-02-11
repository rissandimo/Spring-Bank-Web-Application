package com.rissandimo.bankwebapp3.configuration;

import com.rissandimo.bankwebapp3.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    private UserDetailsServiceImpl userDetailsService;

   public SecurityConfiguration(UserDetailsServiceImpl userDetailsService)
   {
       this.userDetailsService =  userDetailsService;
   }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .antMatchers("/users/createNewUser").permitAll()
                .antMatchers("/users/processNewUser").permitAll()
                .anyRequest().authenticated()

                .and()

                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/users/showUser")
                .permitAll()

                .and()

                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .clearAuthentication(true)
                .invalidateHttpSession(true)

                .and()

                .exceptionHandling().accessDeniedPage("/accessDenied");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }
}
