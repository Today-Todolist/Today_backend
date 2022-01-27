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

    private static ExceptionHandlerFilter exceptionHandlerFilter
    private HttpServletRequest request = Stub(HttpServletRequest)
    private HttpServletResponse response = Mock(HttpServletResponse)
    private FilterChain chain = Stub(FilterChain)
    private PrintWriter printWriter = Stub(PrintWriter)

    def setupSpec() {
        ObjectMapper objectMapper = Stub(ObjectMapper)
        exceptionHandlerFilter = new ExceptionHandlerFilter(objectMapper)
        objectMapper.writeValueAsString(_) >> "json"
    }

    def "test handleException" () {
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