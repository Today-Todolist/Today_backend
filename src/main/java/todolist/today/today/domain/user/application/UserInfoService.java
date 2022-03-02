package todolist.today.today.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.user.dao.CustomUserRepositoryImpl;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.dto.response.MyInfoResponse;
import todolist.today.today.domain.user.dto.response.UserInfoResponse;
import todolist.today.today.domain.user.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoService {

    private final CustomUserRepositoryImpl customUserRepository;
    private final UserRepository userRepository;

    public MyInfoResponse getMyInfo(String userId) {
        return customUserRepository.getMyInfo(userId);
    }

    public UserInfoResponse getUserInfo(String userId, String myId) {
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        return customUserRepository.getUserInfo(userId, myId);
    }

}
