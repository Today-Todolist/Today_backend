package todolist.today.today.global.error.ingredient;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TestController {

    @PostMapping("/exception")
    public void exceptionTesting(@Valid @RequestBody TestDto dto, @RequestParam("test") String test) throws Exception {
        throw new Exception();
    }

    @GetMapping("/basic")
    public void basicExceptionTesting() {
        throw new BasicTestException();
    }

    @GetMapping("/involute")
    public void involuteExceptionTesting() {
        throw new InvoluteTestException();
    }

    @GetMapping("/simple")
    public void simpleExceptionTesting() {
        throw new SimpleTestException();
    }

    @GetMapping("/auth")
    public void authTesting() {
        throw new AccessDeniedException("test");
    }

}
