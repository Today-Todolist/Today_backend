package todolist.today.today.global.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;
import todolist.today.today.global.error.exception.security.ImpossibleToGetIpException;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestBucketProvider {

    private final Map<String, Bucket> requestBuckets = new ConcurrentHashMap<>();

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        return ip == null || ip.isBlank() ? request.getRemoteAddr() : ip;
    }

    public Bucket resolveBucket(HttpServletRequest request) {
        String ip = getClientIp(request);
        if (ip == null || ip.isBlank()) throw new ImpossibleToGetIpException();
        return requestBuckets.computeIfAbsent(ip, (ignore) -> newBucket());
    }

    private Bucket newBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(100, Refill.greedy(20, Duration.ofMinutes(1))))
                .build();
    }

}
