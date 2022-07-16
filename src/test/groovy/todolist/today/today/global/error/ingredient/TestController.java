package todolist.today.today.global.error.ingredient;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController @Validated
public class TestController {

    @PostMapping("/exception")
    public void exceptionTesting(@Valid @RequestBody TestDto dto, @RequestParam("test") @Positive int param) throws Exception {
        throw new Exception();
    }

    @PostMapping("/multipart-exception")
    public void multipartExceptionTesting(@RequestPart("test") MultipartFile file) {}

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
