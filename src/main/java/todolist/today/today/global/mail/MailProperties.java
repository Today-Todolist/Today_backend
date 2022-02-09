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
    private final StringBuilder signUpLink;
    private final StringBuilder changePasswordLink;

    public MailProperties(String username, StringBuilder signUpLink, StringBuilder changePasswordLink) throws UnsupportedEncodingException {
        this.fromAddress = new InternetAddress(username, "오늘");
        this.signUpLink = signUpLink.append("?token=");
        this.changePasswordLink = changePasswordLink.append("?token=");
    }

}
