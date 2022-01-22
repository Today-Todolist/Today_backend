package todolist.today.today.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import todolist.today.today.global.error.dto.BasicErrorResponse;
import todolist.today.today.global.error.exception.BasicException;
import todolist.today.today.global.error.exception.InvoluteException;
import todolist.today.today.global.error.exception.SimpleException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (BasicException | SimpleException | InvoluteException e) {
            response.setStatus(getStatus(e.getErrorResponse()));
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(e.getErrorResponse()));
        }
    }

    private <R extends BasicErrorResponse> int getStatus(R errorResponse) throws IOException {
        return errorResponse.getStatus();
    }

}