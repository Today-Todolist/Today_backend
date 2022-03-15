package todolist.today.today.infra.mail.properties

import spock.lang.Specification

class MailPropertiesTest extends Specification {

    static final String USERNAME = "today043149@gmail.com"
    static final String SIGN_UP_LINK = "https://signup"
    static final String CHANGE_PASSOWRD_LINK = "https://changepassword"

    def "test create MailProperties" () {
        when:
        MailProperties mailProperties = new MailProperties(USERNAME, SIGN_UP_LINK, CHANGE_PASSOWRD_LINK)

        then:
        mailProperties.getFromAddress().getAddress() == USERNAME
        mailProperties.getFromAddress().getPersonal() == "오늘"
        mailProperties.getSignUpLink() == SIGN_UP_LINK
        mailProperties.getChangePasswordLink() == CHANGE_PASSOWRD_LINK
    }

}