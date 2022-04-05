package todolist.today.today.domain.check.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.friend.dao.CustomFriendApplyRepository;
import todolist.today.today.domain.friend.dao.CustomFriendRepository;
import todolist.today.today.domain.friend.exception.FriendAlreadyExistsException;
import todolist.today.today.domain.friend.exception.FriendApplyAlreadyExistsException;
import todolist.today.today.domain.friend.exception.FriendApplyNotFoundException;
import todolist.today.today.domain.template.dao.TemplateRepository;
import todolist.today.today.domain.template.exception.TemplateAlreadyExistException;
import todolist.today.today.domain.user.dao.CustomUserRepository;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.dao.redis.SignUpCertifyRepository;
import todolist.today.today.domain.user.exception.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckService {

    private final SignUpCertifyRepository signUpCertifyRepository;
    private final UserRepository userRepository;
    private final CustomUserRepository customUserRepository;
    private final TemplateRepository templateRepository;
    private final CustomFriendRepository customFriendRepository;
    private final CustomFriendApplyRepository customFriendApplyRepository;

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

    public void checkNotExistsUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
    }

    public void checkExistsFriendApply(String userId, String myId) {
        if (customFriendApplyRepository.existsFriendApply(userId, myId)) {
            throw new FriendApplyAlreadyExistsException(userId, myId);
        }
    }

    public void checkExistsFriend(String userId, String myId) {
        if (customFriendRepository.existsFriend(userId, myId)) {
            throw new FriendAlreadyExistsException(userId, myId);
        }
    }

    public void checkNotExistsFriendApply(String userId, String myId) {
        if (!customFriendApplyRepository.existsFriendApply(userId, myId)) {
            throw new FriendApplyNotFoundException(userId, myId);
        }
    }

}
