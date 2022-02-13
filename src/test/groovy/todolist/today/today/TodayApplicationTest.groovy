package todolist.today.today

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class TodayApplicationTest extends Specification {

    def "contextLoads" () {
        expect:
        true
    }

}