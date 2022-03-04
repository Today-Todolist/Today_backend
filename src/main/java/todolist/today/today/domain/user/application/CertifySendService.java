package todolist.today.today.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.check.CheckService;
import todolist.today.today.domain.user.dao.CustomUserRepositoryImpl;
import todolist.today.today.domain.user.dao.redis.ChangePasswordCertifyRepository;
import todolist.today.today.domain.user.dao.redis.SignUpCertifyRepository;
import todolist.today.today.domain.user.domain.redis.ChangePasswordCertify;
import todolist.today.today.domain.user.domain.redis.SignUpCertify;
import todolist.today.today.domain.user.dto.request.ChangePasswordCertifySendRequest;
import todolist.today.today.domain.user.dto.request.SignUpCertifySendRequest;
import todolist.today.today.domain.user.exception.UserNotFoundException;
import todolist.today.today.infra.mail.MailContentProvider;
import todolist.today.today.infra.mail.MailSendFacade;

@Service
@RequiredArgsConstructor
@Transactional
public class CertifySendService {

    private final CheckService checkService;

    private final SignUpCertifyRepository signUpCertifyRepository;
    private final ChangePasswordCertifyRepository changePasswordCertifyRepository;
    private final CustomUserRepositoryImpl customUserRepository;

    private final MailContentProvider mailContentProvider;
    private final MailSendFacade mailSendFacade;

    private final PasswordEncoder passwordEncoder;

    public void sendSignUpCertify(SignUpCertifySendRequest request) {
        String userId = request.getEmail();
        checkService.checkExistsEmail(userId);

        String nickname = request.getNickname();
        checkService.checkExistsNickname(nickname);

        SignUpCertify signUpCertify = SignUpCertify.builder()
                .email(userId)
                .nickname(nickname)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        signUpCertifyRepository.save(signUpCertify);

        String content = mailContentProvider.createSignUpContent(String.valueOf(signUpCertify.getId()));
        mailSendFacade.sendHtmlMail(userId, nickname + "님을 위한 오늘 회원가입 인증이 도착했습니다", content);
    }

    public void sendChangePasswordCertify(ChangePasswordCertifySendRequest request) {
        String userId = request.getEmail();

        String nickname = customUserRepository.findNicknameById(userId);
        if (nickname == null) {
            throw new UserNotFoundException(userId);
        }

        ChangePasswordCertify changePasswordCertify = ChangePasswordCertify.builder()
                .email(userId)
                .password(passwordEncoder.encode(request.getNewPassword()))
                .build();
        changePasswordCertifyRepository.save(changePasswordCertify);

        String content = mailContentProvider.createChangePasswordContent(String.valueOf(changePasswordCertify.getId()));
        mailSendFacade.sendHtmlMail(userId, nickname + "님을 위한 오늘 비밀번호 재설정 인증이 도착했습니다", content);
    }

}
