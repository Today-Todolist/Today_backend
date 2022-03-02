package todolist.today.today.domain.friend.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.friend.dao.FriendApplyRepository;
import todolist.today.today.domain.friend.domain.FriendApply;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.domain.User;
import todolist.today.today.domain.user.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendSettingService {

    private final UserRepository userRepository;
    private final FriendApplyRepository friendApplyRepository;

    public void applyFriend(String userId, String myId) {
        User friend = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        User user = userRepository.findById(myId)
                .orElseThrow(() -> new UserNotFoundException(myId));

        FriendApply friendApply = FriendApply.builder()
                .friend(friend)
                .user(user)
                .build();
        friendApplyRepository.save(friendApply);
    }

}
