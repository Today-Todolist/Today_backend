package todolist.today.today.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.user.application.CertifySendService;
import todolist.today.today.domain.user.dto.request.SignUpCertifySendRequest;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CertifySendController {

    private final CertifySendService certifySendService;

    @PostMapping("/sign-up/email")
    public void sendSignUpCertify(@Valid @RequestBody SignUpCertifySendRequest request) {
        certifySendService.sendSignUpCertify(request);
    }

}
