package zootecpro.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import zootecpro.backend.gui.App;

@SpringBootApplication
public class BackendApplication {

  public static void main(String[] args) {
    App app = new App(() -> {
      return SpringApplication.run(BackendApplication.class, args);
    });
    app.start();
  }

}
