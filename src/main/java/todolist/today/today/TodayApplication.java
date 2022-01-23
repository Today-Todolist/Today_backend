package todolist.today.today;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.Locale;

@ConfigurationPropertiesScan
@SpringBootApplication
public class TodayApplication {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "us"));
        SpringApplication.run(TodayApplication.class, args);
    }

}
