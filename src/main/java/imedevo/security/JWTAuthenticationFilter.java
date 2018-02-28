package imedevo.security;

import static imedevo.security.SecurityConstants.EXPIRATION_TIME;
import static imedevo.security.SecurityConstants.HEADER_STRING;
import static imedevo.security.SecurityConstants.SECRET;
import static imedevo.security.SecurityConstants.TOKEN_PREFIX;

import com.fasterxml.jackson.databind.ObjectMapper;
import imedevo.httpStatuses.UserStatus;
import imedevo.model.AppUser;
import imedevo.repository.UserRepository;
import imedevo.service.CustomUserDetailService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configurable
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  private CustomUserDetailService customUserDetailService;

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  UserRepository userRepository;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
      CustomUserDetailService customUserDetailService) {
    this.authenticationManager = authenticationManager;
    this.customUserDetailService = customUserDetailService;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      AppUser appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {

    ZonedDateTime expirationTimeUTC = ZonedDateTime.now(ZoneOffset.UTC).plus(EXPIRATION_TIME,
        ChronoUnit.MILLIS);
    String username = ((org.springframework.security.core.userdetails.User) authResult
        .getPrincipal())
        .getUsername();

    String token = Jwts.builder()
        .setSubject(username)
        .setExpiration(Date.from(expirationTimeUTC.toInstant()))
        .signWith(SignatureAlgorithm.HS256, SECRET)
        .compact();

    AppUser appUser = customUserDetailService.loadAppUser(username);

    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();

    JSONObject jsonResponse = new JSONObject();
    Map<String, Object> mapResponse = new HashMap<>();
    mapResponse.put("user", appUser);
    mapResponse.put("status", UserStatus.LOGIN_OK);
    mapResponse.put("token", TOKEN_PREFIX + token);

    jsonResponse.put("response", mapResponse);

    out.print(jsonResponse.toString());

    //    response.getWriter().write(token);
    response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
  }
}