package automation.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadUtils {

  private ThreadUtils() {
  }

  public static void sleepXMillis(int timeoutInMillis) {
    try {
      Thread.sleep(timeoutInMillis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      Logger logger = LoggerFactory.getLogger(ThreadUtils.class);
      logger.error(e.toString());
    }
  }

  public static void sleepXSeconds(int timeoutInSeconds) {
    sleepXMillis(timeoutInSeconds * 1000);
  }

  public static void sleepXMinutes(int timeoutInMinutes) {
    sleepXMillis(timeoutInMinutes * 60000);
  }
}
