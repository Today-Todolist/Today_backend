package todolist.today.today.infra.mail;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import todolist.today.today.infra.mail.properties.MailProperties;

import java.io.IOException;

@Component
public class MailContentProvider {

    private final String signUpTemplate;
    private final String changePasswordTemplate;

    public MailContentProvider(MailProperties mailProperties) throws IOException {
        signUpTemplate = getResourceByPath("static/SignUpMail.html")
                .replace("{LINK}", mailProperties.getSignUpLink() + "?token={TOKEN}&email={EMAIL}");
        changePasswordTemplate = getResourceByPath("static/ChangePasswordMail.html")
                .replace("{LINK}", mailProperties.getChangePasswordLink() + "?token={TOKEN}&email={EMAIL}");
    }

    private String getResourceByPath(String path) throws IOException {
        byte[] bytes = new ClassPathResource(path).getInputStream().readAllBytes();
        return new String(bytes);
    }

    public String createSignUpContent(String token, String email) {
        return signUpTemplate
                .replace("{TOKEN}", token)
                .replace("{EMAIL}", email);
    }

    public String createChangePasswordContent(String token, String email) {
        return changePasswordTemplate
                .replace("{TOKEN}", token)
                .replace("{EMAIL}", email);
    }

}
