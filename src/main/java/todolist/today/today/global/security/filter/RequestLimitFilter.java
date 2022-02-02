package todolist.today.today.global.security.filter;

import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import todolist.today.today.global.error.exception.security.TooManyRequestException;
import todolist.today.today.global.security.RequestBucketProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class RequestLimitFilter  extends OncePerRequestFilter {

    private final RequestBucketProvider requestBucketProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Bucket bucket = requestBucketProvider.resolveBucket(request);
        if (!bucket.tryConsume(1)) {
            throw new TooManyRequestException(bucket.getAvailableTokens());
        }
        chain.doFilter(request,response);
    }

}
