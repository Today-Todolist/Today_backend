package todolist.today.today.global.security.filter

import spock.lang.Specification
import todolist.today.today.global.security.exception.ImpossibleToGetIpException
import todolist.today.today.global.security.exception.TooManyRequestException
import todolist.today.today.global.security.service.RequestBucketProvider

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RequestLimitFilterTest extends Specification {

    RequestLimitFilter requestLimitFilter
    HttpServletRequest request = Stub()
    HttpServletResponse response = Stub()
    FilterChain chain = Mock()

    def setup() {
        requestLimitFilter = new RequestLimitFilter(new RequestBucketProvider())
    }

    def "test prevent many request" () {
        given:
        request.getHeader("X-Forwarded-For") >> "client ip"

        when:
        120.times {
            requestLimitFilter.doFilterInternal(request, response, chain)
        }

        then:
        100 * chain.doFilter(request, response)
        thrown(TooManyRequestException)
    }

    def "test impossible get client ip" () {
        given:
        request.getHeader("X-Forwarded-For") >> ip
        request.getRemoteAddr() >> ip

        when:
        requestLimitFilter.doFilterInternal(request, response, chain)

        then:
        thrown(ImpossibleToGetIpException)

        where:
        ip << [null, "", " "]
    }

}