package todolist.today.today.infra.mail

import org.springframework.mail.javamail.JavaMailSender
import spock.lang.Specification
import todolist.today.today.infra.mail.exception.MailSendFailedException
import todolist.today.today.infra.mail.properties.MailProperties

import javax.mail.MessagingException
import javax.mail.internet.MimeMessage

class MailSendFacadeTest extends Specification {

    MailSendFacade mailSendFacade
    JavaMailSender mailSender = Mock()
    MailProperties mailProperties = Stub()

    def setup() {
        mailSendFacade = new MailSendFacade(mailSender, mailProperties)
    }

    def "test sendHtmlMail" () {
        given:
        final String TO_ADDRESS = "today043149@gmail.com"
        final String TITLE = "testTitle"
        final String CONTENT = "testContent"
        mailSender.createMimeMessage() >> Stub(MimeMessage)

        when:
        mailSendFacade.sendHtmlMail(TO_ADDRESS, TITLE, CONTENT)

        then:
        noExceptionThrown()
        1 * mailSender.send(_)
    }

    def "test sendHtmlMail MailSendFailedException" () {
        given:
        final String TO_ADDRESS = "today043149@gmail.com"
        final String TITLE = "testTitle"
        final String CONTENT = "testContent"
        mailSender.createMimeMessage() >> { throw new MessagingException() }

        when:
        mailSendFacade.sendHtmlMail(TO_ADDRESS, TITLE, CONTENT)

        then:
        thrown(MailSendFailedException)
    }

    def "test sendTextMail" () {
        given:
        final String TO_ADDRESS = "today043149@gmail.com"
        final String TITLE = "testTitle"
        final String CONTENT = "testContent"
        mailSender.createMimeMessage() >> Stub(MimeMessage)

        when:
        mailSendFacade.sendTextMail(TO_ADDRESS, TITLE, CONTENT)

        then:
        noExceptionThrown()
        1 * mailSender.send(_)
    }

    def "test sendTextMail MailSendFailedException" () {
        given:
        final String TO_ADDRESS = "today043149@gmail.com"
        final String TITLE = "testTitle"
        final String CONTENT = "testContent"
        mailSender.createMimeMessage() >> { throw new MessagingException() }

        when:
        mailSendFacade.sendTextMail(TO_ADDRESS, TITLE, CONTENT)

        then:
        thrown(MailSendFailedException)
    }

}