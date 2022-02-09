package todolist.today.today.global.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MailContentProvider {

    private static String signUpTemplate;
    private static String changePasswordTemplate;

    private final MailProperties mailProperties;

    static {
        try {
            signUpTemplate = getResourceByPath("static/SignUpMail.html");
            changePasswordTemplate = getResourceByPath("static/ChangePasswordMail.html");
        } catch (IOException ignore) {}
    }

    private static String getResourceByPath(String path) throws IOException {
        byte[] bytes = new ClassPathResource(path).getInputStream().readAllBytes();
        return new String(bytes);
    }

    public String createSignUpContent(String token) {
        return signUpTemplate
                .replaceAll("\\{LINK}", mailProperties.getSignUpLink().append(token).toString());
    }

    public String createChangePasswordContent(String token) {
        return changePasswordTemplate
                .replaceAll("\\{LINK}", mailProperties.getChangePasswordLink().append(token).toString());
    }

}
