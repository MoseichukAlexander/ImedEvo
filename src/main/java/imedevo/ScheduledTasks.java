package imedevo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.time.LocalDateTime;

import imedevo.repository.TemporaryTokenRepository;

@EnableScheduling
public class ScheduledTasks {

  private final int hours = 36;

  @Autowired
  private TemporaryTokenRepository temporaryTokenRepository;

  @Scheduled(cron = "0 0 1 * * *")
  public void startCleaner() {
    System.out.println("\nTOKENS CLEANER START!!!");
    temporaryTokenRepository.deleteByExpirationDateBefore(LocalDateTime.now().minusHours(hours));
  }

  @Bean
  public TaskScheduler taskScheduler() {
    return new ConcurrentTaskScheduler(); //single threaded by default
  }
}