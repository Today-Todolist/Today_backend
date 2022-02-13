package todolist.today.today.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

    @PostConstruct
    public void setTimezone(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

}
