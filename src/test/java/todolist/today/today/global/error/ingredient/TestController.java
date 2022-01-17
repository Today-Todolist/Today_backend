package todolist.today.today.global.error.ingredient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TestController {

    @PostMapping("/exception")
    public void exceptionTesting(@Valid @RequestBody TestDto dto) throws Exception {
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

}
