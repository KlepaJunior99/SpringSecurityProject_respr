package appDir.springsecurityApp.configs;

import appDir.springsecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PersonDetailsService personDetailsService;
    private final SuccessUserHandler successUserHandler;
    private final CustomUrlLogoutSuccessHandler urlLogoutSuccessHandler;
    @Autowired
    public WebSecurityConfig(PersonDetailsService personDetailsService, SuccessUserHandler successUserHandler, CustomUrlLogoutSuccessHandler urlLogoutSuccessHandler) {
        this.personDetailsService = personDetailsService;
        this.successUserHandler = successUserHandler;
        this.urlLogoutSuccessHandler = urlLogoutSuccessHandler;
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin().loginPage("/auth/login")
                .successHandler(successUserHandler)
                .permitAll()
                .loginProcessingUrl("/process_login")
                .failureUrl("/login?error");
        http.logout()
                .permitAll() // разрешаем делать логаут всем
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/auth/login")
                .logoutSuccessHandler(urlLogoutSuccessHandler);
    }
}