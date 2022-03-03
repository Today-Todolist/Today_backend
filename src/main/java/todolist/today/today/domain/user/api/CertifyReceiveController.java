package todolist.today.today.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.user.application.CertifyReceiveService;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@RestController @Validated
@RequiredArgsConstructor
public class CertifyReceiveController {

    private final CertifyReceiveService certifyReceiveService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void receiveSignUpCertify(@RequestParam("email") @Email @Size(min = 1, max = 64) String email,
                                     @RequestParam("token") long token) {
        certifyReceiveService.receiveSignUpCertify(email, token);
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.CREATED)
    public void receiveChangePasswordCertify(@RequestParam("email") @Email @Size(min = 1, max = 64) String email,
                                             @RequestParam("token") long token) {
        certifyReceiveService.receiveChangePasswordCertify(email, token);
    }

}
