package todolist.today.today;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Locale;

@EnableAsync
@ConfigurationPropertiesScan
@SpringBootApplication
public class TodayApplication {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "us"));
        SpringApplication.run(TodayApplication.class, args);
    }

}
