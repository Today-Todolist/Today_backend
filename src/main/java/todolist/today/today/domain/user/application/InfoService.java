package todolist.today.today.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import todolist.today.today.domain.user.dao.CustomUserRepositoryImpl;
import todolist.today.today.domain.user.dto.response.MyInfoResponse;
import todolist.today.today.domain.user.dto.response.UserInfoResponse;

@Service
@RequiredArgsConstructor
public class InfoService {

    private final CustomUserRepositoryImpl customUserRepository;

    public MyInfoResponse getMyInfo(String userId) {
        return customUserRepository.getMyInfo(userId);
    }

    public UserInfoResponse getUserInfo(String userId, String myId) {
        return customUserRepository.getUserInfo(userId, myId);
    }

}