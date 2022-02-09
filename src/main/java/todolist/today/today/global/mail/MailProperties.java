package todolist.today.today.global.mail;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {

    private final InternetAddress fromAddress;

    public MailProperties(String fromAddress) throws UnsupportedEncodingException {
        this.fromAddress = new InternetAddress(fromAddress, "오늘");
    }

}
