package imedevo.security;

import static imedevo.security.SecurityConstants.HEADER_STRING;
import static imedevo.security.SecurityConstants.SECRET;
import static imedevo.security.SecurityConstants.TOKEN_PREFIX;

import imedevo.service.CustomUserDetailService;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  private CustomUserDetailService customUserDetailService;

  public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
      CustomUserDetailService customUserDetailService) {
    super(authenticationManager);
    this.customUserDetailService = customUserDetailService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain) throws IOException, ServletException {
    String header = request.getHeader(HEADER_STRING);
    if (header == null || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }
    UsernamePasswordAuthenticationToken usernamePasswordAuth = getAuthenticationToken(request);
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuth);
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    if (token == null) {
      return null;
    }
    String username = Jwts.parser().setSigningKey(SECRET)
        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
        .getBody()
        .getSubject();
    UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
//    AppUser appUser = customUserDetailService.loadAppUserByUsername(username);
    return username != null ? new UsernamePasswordAuthenticationToken(username, null,
        userDetails.getAuthorities()) : null;
  }
}
