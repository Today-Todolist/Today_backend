package todolist.today.today.global.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Configuration
public class LanguageConfig {

    @PostConstruct
    public void setLanguage() {
        Locale.setDefault(new Locale("en", "us"));
    }

}
