package todolist.today.today.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.dao.redis.ChangePasswordCertifyRepository;
import todolist.today.today.domain.user.dao.redis.SignUpCertifyRepository;
import todolist.today.today.domain.user.domain.User;
import todolist.today.today.domain.user.domain.redis.ChangePasswordCertify;
import todolist.today.today.domain.user.domain.redis.SignUpCertify;
import todolist.today.today.domain.user.exception.UserNotFoundException;
import todolist.today.today.domain.user.exception.WrongCertifyException;
import todolist.today.today.infra.file.image.ImageUploadFacade;

@Service
@RequiredArgsConstructor
@Transactional
public class CertifyReceiveService {

    private final SignUpCertifyRepository signUpCertifyRepository;
    private final ChangePasswordCertifyRepository changePasswordCertifyRepository;
    private final UserRepository userRepository;

    private final ImageUploadFacade imageUploadFacade;

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
        signUpCertifyRepository.delete(signUpCertify);
    }

    public void receiveChangePasswordCertify(String email, long token) {
        ChangePasswordCertify changePasswordCertify = changePasswordCertifyRepository.findById(token)
                .filter(certify -> certify.getEmail().equals(email))
                .orElseThrow(WrongCertifyException::new);

        User user = userRepository.findById(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        user.changePassword(changePasswordCertify.getPassword());
        changePasswordCertifyRepository.delete(changePasswordCertify);
    }

}
