package todolist.today.today.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.dao.redis.SignUpCertifyRepository;
import todolist.today.today.domain.user.exception.NicknameAlreadyExistException;
import todolist.today.today.domain.user.exception.UserAlreadyExistException;

@Service
@RequiredArgsConstructor
public class CheckService {

    private final SignUpCertifyRepository signUpCertifyRepository;
    private final UserRepository userRepository;

    public void checkEmail(String email) {
        if (signUpCertifyRepository.existsByEmail(email) || userRepository.existsById(email)) {
            throw new UserAlreadyExistException(email);
        }
    }

    public void checkNickname(String nickname) {
        if (signUpCertifyRepository.existsByNickname(nickname) || userRepository.existsByNickname(nickname)) {
            throw new NicknameAlreadyExistException(nickname);
        }
    }

}
