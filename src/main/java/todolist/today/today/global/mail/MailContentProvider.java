package todolist.today.today.global.mail;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MailContentProvider {

    private final String signUpTemplate;
    private final String changePasswordTemplate;

    public MailContentProvider(MailProperties mailProperties) throws IOException {
        signUpTemplate = getResourceByPath("static/SignUpMail.html")
                .replaceAll("\\{LINK}", mailProperties.getSignUpLink() + "?token={TOKEN}");
        changePasswordTemplate = getResourceByPath("static/ChangePasswordMail.html")
                .replaceAll("\\{LINK}", mailProperties.getChangePasswordLink() + "?token={TOKEN}");
    }

    private String getResourceByPath(String path) throws IOException {
        byte[] bytes = new ClassPathResource(path).getInputStream().readAllBytes();
        return new String(bytes);
    }

    public String createSignUpContent(String token) {
        return signUpTemplate
                .replaceAll("\\{TOKEN}", token);
    }

    public String createChangePasswordContent(String token) {
        return changePasswordTemplate
                .replaceAll("\\{TOKEN}", token);
    }

}
