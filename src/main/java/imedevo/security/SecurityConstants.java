package imedevo.security;

public class SecurityConstants {

  public static final String SECRET = "secre";
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final long EXPIRATION_TIME = 864_000_000L; // 10 days
//  public static final long EXPIRATION_TIME = 60000L; // 1 minute

//  public static void main(String[] args) {
//    System.out.println(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
//  }
}
