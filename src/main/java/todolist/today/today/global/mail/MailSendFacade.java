package todolist.today.today.global.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import todolist.today.today.global.error.exception.mail.MailSendFailedException;

import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class MailSendFacade {

    @Value("${spring.mail.username}")
    private String fromAddress;

    private final JavaMailSender mailSender;

    public void sendHtmlMail(String toAddress, String title, String content) {
        try {
            sendMail(toAddress, title, content, true);
        } catch (Exception e) {
            throw new MailSendFailedException();
        }
    }

    public void sendTextMail(String toAddress, String title, String content) {
        try {
            sendMail(toAddress, title, content, false);
        } catch (Exception e) {
            throw new MailSendFailedException();
        }
    }

    private void sendMail(String toAddress, String title, String content, boolean isHtml) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, false, "UTF-8");

        messageHelper.setTo(toAddress);
        messageHelper.setFrom(fromAddress);
        messageHelper.setSubject(title);

        messageHelper.setText(content, isHtml);
        mailSender.send(message);
    }

}
