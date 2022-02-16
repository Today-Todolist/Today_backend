package todolist.today.today.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.dao.redis.SignUpCertifyRepository;
import todolist.today.today.domain.user.domain.User;
import todolist.today.today.domain.user.domain.redis.SignUpCertify;
import todolist.today.today.domain.user.exception.WrongCertifyException;
import todolist.today.today.infra.file.image.ImageUploadFacade;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CertifyReceiveService {

    private final SignUpCertifyRepository signUpCertifyRepository;
    private final UserRepository userRepository;

    private ImageUploadFacade imageUploadFacade;

    public void receiveSignUpCertify(String email, long token) {
        SignUpCertify signUpCertify = signUpCertifyRepository.findById(token)
                .filter(certify -> certify.getEmail().equals(email))
                .orElseThrow(WrongCertifyException::new);

        User user = User.builder()
                .email(email)
                .password(signUpCertify.getPassword())
                .nickname(signUpCertify.getNickname())
                .profile(imageUploadFacade.uploadRandomImage())
                .build();
        userRepository.save(user);
    }

}
