package todolist.today.today.global.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import todolist.today.today.global.error.ingredient.BasicTestException
import todolist.today.today.global.error.ingredient.InvoluteTestException
import todolist.today.today.global.error.ingredient.SimpleTestException

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ExceptionHandlerFilterTest extends Specification {

    ExceptionHandlerFilter exceptionHandlerFilter
    HttpServletRequest request = Stub()
    HttpServletResponse response = Mock()
    FilterChain chain = Mock()
    PrintWriter printWriter = Stub()

    def setup() {
        ObjectMapper objectMapper = Stub()
        exceptionHandlerFilter = new ExceptionHandlerFilter(objectMapper)
        objectMapper.writeValueAsString(_) >> "json"
    }

    def "test not handle exception" () {
        when:
        exceptionHandlerFilter.doFilterInternal(request, response, chain)

        then:
        1 * chain.doFilter(request, response)
    }

    def "test handle exception" () {
        given:
        chain.doFilter(request, response) >> { throw exception }

        when:
        exceptionHandlerFilter.doFilterInternal(request, response, chain)

        then:
        1 * response.setStatus(500)
        1 * response.setContentType("application/json")
        1 * response.getWriter() >> printWriter

        where:
        exception << [new BasicTestException(), new InvoluteTestException(), new SimpleTestException()]
    }

}