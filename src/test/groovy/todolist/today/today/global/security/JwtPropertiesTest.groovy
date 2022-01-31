package todolist.today.today.global.security

import spock.lang.Specification


class JwtPropertiesTest extends Specification {

    private JwtProperties jwtProperties
    private final String SECRET = "asdf"
    private final Long ACCESS_EXP = 1000L
    private final Long REFRESH_EXP = 2000L

    def "test create JwtProperties" () {
        when:
        jwtProperties = new JwtProperties(SECRET, ACCESS_EXP, REFRESH_EXP)

        then:
        jwtProperties.getSecret() == Base64.getEncoder().encodeToString(SECRET.getBytes())
        jwtProperties.getAccessExp() == ACCESS_EXP
        jwtProperties.getRefreshExp() == REFRESH_EXP
    }

}