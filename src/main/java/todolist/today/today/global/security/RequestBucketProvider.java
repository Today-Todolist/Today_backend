package todolist.today.today.global.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestBucketProvider {

    private final Map<String, Bucket> requestBuckets = new ConcurrentHashMap<>();

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        return ip != null ? ip : request.getRemoteAddr();
    }

    public Bucket resolveBucket(HttpServletRequest httpServletRequest) {
        return requestBuckets.putIfAbsent(getClientIp(httpServletRequest), newBucket());
    }

    private Bucket newBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(100, Refill.greedy(20, Duration.ofMinutes(1))))
                .build();
    }

}
