package todolist.today.today.global.security.filter

import spock.lang.Specification
import todolist.today.today.global.error.exception.security.ImpossibleToGetIpException
import todolist.today.today.global.error.exception.security.TooManyRequestException
import todolist.today.today.global.security.RequestBucketProvider

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RequestLimitFilterTest extends Specification {

    private static RequestLimitFilter requestLimitFilter;
    private HttpServletRequest request = Stub(HttpServletRequest)
    private HttpServletResponse response = Stub(HttpServletResponse)
    private FilterChain chain = Mock(FilterChain)

    def setupSpec() {
        RequestBucketProvider requestBucketProvider = new RequestBucketProvider()
        requestLimitFilter = new RequestLimitFilter(requestBucketProvider)
    }

    def "test preventManyRequest" () {
        given:
        request.getRemoteAddr() >> "client ip"

        when:
        120.times {
            requestLimitFilter.doFilterInternal(request, response, chain)
        }

        then:
        100 * chain.doFilter(request, response)
        thrown(TooManyRequestException)
    }

    def "test impossibleClientIp" () {
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