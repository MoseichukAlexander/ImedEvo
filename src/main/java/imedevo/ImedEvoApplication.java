package imedevo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImedEvoApplication {

  public static void main(String[] args) {
    SpringApplication.run(new Object[]{ImedEvoApplication.class, ScheduledTasks.class}, args);
  }
}