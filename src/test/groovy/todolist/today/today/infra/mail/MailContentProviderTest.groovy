package todolist.today.today.infra.mail

import spock.lang.Specification
import todolist.today.today.infra.mail.properties.MailProperties


class MailContentProviderTest extends Specification {

    MailContentProvider mailContentProvider
    MailProperties mailProperties = Stub()
    static final String SIGN_UP_LINK = "https://signup"
    static final String CHANGE_PASSOWRD_LINK = "https://changepassword"

    def setup() {
        mailProperties.getSignUpLink() >> SIGN_UP_LINK
        mailProperties.getChangePasswordLink() >> CHANGE_PASSOWRD_LINK
        mailContentProvider = new MailContentProvider(mailProperties)
    }

    def "test createSignUpContent" () {
        given:
        final String TOKEN = "1234"

        when:
        String content = mailContentProvider.createSignUpContent(TOKEN)

        then:
        content == SIGN_UP_LINK + "?token=" + TOKEN
    }

    def "test createChangePasswordContent" () {
        given:
        final String TOKEN = "1234"

        when:
        String content = mailContentProvider.createChangePasswordContent(TOKEN)

        then:
        content == CHANGE_PASSOWRD_LINK + "?token=" + TOKEN
    }

}