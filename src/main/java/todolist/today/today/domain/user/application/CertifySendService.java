package todolist.today.today.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.dao.redis.SignUpCertifyRepository;
import todolist.today.today.domain.user.domain.redis.SignUpCertify;
import todolist.today.today.domain.user.dto.request.SignUpCertifySendRequest;
import todolist.today.today.domain.user.exception.EmailAlreadyExistException;
import todolist.today.today.domain.user.exception.NicknameAlreadyExistException;
import todolist.today.today.infra.mail.MailContentProvider;
import todolist.today.today.infra.mail.MailSendFacade;

@Service
@RequiredArgsConstructor
@Transactional
public class CertifySendService {

    private final SignUpCertifyRepository signUpCertifyRepository;
    private final UserRepository userRepository;

    private final MailContentProvider mailContentProvider;
    private final MailSendFacade mailSendFacade;

    public void sendSignUpCertify(SignUpCertifySendRequest request) {
        String userId = request.getEmail();
        if (userRepository.existsById(userId) || signUpCertifyRepository.existsByEmail(userId)) {
            throw new EmailAlreadyExistException(userId);
        }

        String nickname = request.getNickname();
        if (userRepository.existsByNickname(nickname) || signUpCertifyRepository.existsByNickname(nickname)) {
            throw new NicknameAlreadyExistException(nickname);
        }

        SignUpCertify signUpCertify = SignUpCertify.builder()
                .email(userId)
                .nickname(nickname)
                .password(request.getPassword())
                .build();
        signUpCertifyRepository.save(signUpCertify);

        String content = mailContentProvider.createSignUpContent(String.valueOf(signUpCertify.getId()));
        mailSendFacade.sendHtmlMail(userId, nickname + "님을 위한 오늘 회원가입 인증이 도착했습니다", content);
    }

}
