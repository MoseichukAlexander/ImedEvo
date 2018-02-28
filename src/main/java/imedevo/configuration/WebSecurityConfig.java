package imedevo.configuration;


import imedevo.security.JWTAuthenticationFilter;
import imedevo.security.JWTAuthorizationFilter;
import imedevo.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring security configuration.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  private final CustomUserDetailService customUserDetailService;

  @Autowired
  public WebSecurityConfig(CustomUserDetailService customUserDetailService) {
    this.customUserDetailService = customUserDetailService;
  }

//  @Override
//  public void configure(WebSecurity web) throws Exception {
//    web.ignoring()
//        .antMatchers("/assets/**", "/index.html", "/**", "/bootstrap/**", "/css/**");
//  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
        .cors().and().csrf().disable().authorizeRequests()
        .antMatchers().permitAll()
        .antMatchers("/resources/**").permitAll()
        .antMatchers("/**").permitAll()
        .antMatchers("/login**").permitAll()
        .antMatchers(HttpMethod.POST, "/users/registration").permitAll()
        .antMatchers(HttpMethod.GET, "/doctors/getall").permitAll()
        .antMatchers(HttpMethod.GET, "/doctors/*").permitAll()
        .antMatchers(HttpMethod.GET, "/clinics/getall").permitAll()
        .antMatchers(HttpMethod.GET, "/clinics/*").permitAll()
        .antMatchers(HttpMethod.GET, "/laboratories/getall").permitAll()
        .antMatchers(HttpMethod.GET, "/laboratories/*").permitAll()
        .antMatchers(HttpMethod.GET, "/diagnostics/getall").permitAll()
        .antMatchers(HttpMethod.GET, "/diagnostics/*").permitAll()
        .antMatchers(HttpMethod.POST, "/forgot/reset").permitAll()
        .antMatchers(HttpMethod.POST, "/forgot/newpassword").permitAll()
        .antMatchers(HttpMethod.GET, "/search/byanyparams").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .usernameParameter("username").passwordParameter("password")
        .permitAll()
        .and()
        .logout().logoutUrl("/logout").deleteCookies("JSESSIONID")
        .clearAuthentication(true).logoutSuccessUrl("/login")
        .and()
        .addFilter(new JWTAuthenticationFilter(authenticationManager(), customUserDetailService))
        .addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailService));
//        .csrf()
//        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailService).passwordEncoder(bCryptPasswordEncoder);
  }
}
