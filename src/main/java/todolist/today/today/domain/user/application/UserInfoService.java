package todolist.today.today.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.user.dao.CustomUserRepositoryImpl;
import todolist.today.today.domain.user.dto.response.MyInfoResponse;
import todolist.today.today.domain.user.dto.response.UserInfoResponse;
import todolist.today.today.domain.user.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoService {

    private final CustomUserRepositoryImpl customUserRepository;

    public MyInfoResponse getMyInfo(String userId) {
        return customUserRepository.getMyInfo(userId);
    }

    public UserInfoResponse getUserInfo(String userId, String myId) {
        try {
            return customUserRepository.getUserInfo(userId, myId);
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new UserNotFoundException(userId);
        }
    }

}
