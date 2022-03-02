package todolist.today.today.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.template.dao.TemplateRepository;
import todolist.today.today.domain.template.exception.TemplateAlreadyExistException;
import todolist.today.today.domain.user.dao.CustomUserRepositoryImpl;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.dao.redis.SignUpCertifyRepository;
import todolist.today.today.domain.user.exception.AuthenticationFailedException;
import todolist.today.today.domain.user.exception.NicknameAlreadyExistException;
import todolist.today.today.domain.user.exception.TodolistChangeImpossibleException;
import todolist.today.today.domain.user.exception.UserAlreadyExistException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckService {

    private final SignUpCertifyRepository signUpCertifyRepository;
    private final UserRepository userRepository;
    private final CustomUserRepositoryImpl customUserRepository;
    private final TemplateRepository templateRepository;

    private final PasswordEncoder passwordEncoder;

    public void checkExistsEmail(String email) {
        if (signUpCertifyRepository.existsByEmail(email) || userRepository.existsById(email)) {
            throw new UserAlreadyExistException(email);
        }
    }

    public void checkExistsNickname(String nickname) {
        if (signUpCertifyRepository.existsByNickname(nickname) || userRepository.existsByNickname(nickname)) {
            throw new NicknameAlreadyExistException(nickname);
        }
    }

    public void checkPassword(String userId, String password) {
        String userPassword = customUserRepository.findPasswordById(userId);
        if (userPassword == null || !passwordEncoder.matches(password, userPassword)) {
            throw new AuthenticationFailedException();
        }
    }

    public void checkExistsTemplateTitle(String userId, String title) {
        if (templateRepository.existsByUserEmailAndTitle(userId, title)) {
            throw new TemplateAlreadyExistException(title);
        }
    }

    public void checkEditAvailability(String userId) {
        if (!customUserRepository.findChangePossibleById(userId)) {
            throw new TodolistChangeImpossibleException();
        }
    }

}
