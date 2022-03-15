package todolist.today.today.global.security.properties

import spock.lang.Specification
import todolist.today.today.global.security.service.properties.JwtProperties


class JwtPropertiesTest extends Specification {

    static final String SECRET = "asdf"
    static final Long ACCESS_EXP = 1000L
    static final Long REFRESH_EXP = 2000L

    def "test create JwtProperties" () {
        when:
        JwtProperties jwtProperties = new JwtProperties(SECRET, ACCESS_EXP, REFRESH_EXP)

        then:
        jwtProperties.getSecret() == Base64.getEncoder().encodeToString(SECRET.getBytes())
        jwtProperties.getAccessExp() == ACCESS_EXP
        jwtProperties.getRefreshExp() == REFRESH_EXP
    }

}