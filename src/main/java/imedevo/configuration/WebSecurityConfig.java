package imedevo.configuration;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring security configuration.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers("/assets/**", "/index.html", "/**", "/bootstrap/**", "/css/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/", "/users/login", "/users/registration", "/doctors/getall", "/doctors/*",
            "/clinics/getall", "/clinics/*", "/forgot/reset", "/forgot/newpassword").permitAll()
        .antMatchers("/users/*", "/users/updateuser")
        .hasAnyAuthority("USER", "SUPER_ADMIN", "DOCTOR", "CLINIC_ADMIN")
        .antMatchers("/doctors/updatedoctor")
        .hasAnyAuthority("DOCTOR", "CLINIC_ADMIN", "SUPER_ADMIN")
        .antMatchers("/users/createdoctor", "/doctors/deletedoctor/*",
            "/clinics/createclinic", "/clinics/updateclinic", "/clinics/deleteclinic/*")
        .hasAnyAuthority("CLINIC_ADMIN", "SUPER_ADMIN")
        .antMatchers("/users/getall")
        .hasAuthority("SUPER_ADMIN")
        .anyRequest().authenticated()
        .and()
        .logout().logoutUrl("/users/logout").deleteCookies("JSESSIONID")
        .clearAuthentication(true).logoutSuccessUrl("/users/login")
        .and()
        .httpBasic()
        .and()
        .cors()
        .and()
        .csrf().disable();
//        .csrf()
//        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery(
            "select u.email as username, u.password, true as enabled from users u "
                + "where u.email=?")
        .authoritiesByUsernameQuery(
            "select u.email as username, r.role from users u "
                + "join user_roles ur "
                + "join roles r "
                + "on r.id=ur.role_id and u.id=ur.user_id "
                + "where u.email=?");
  }
}
