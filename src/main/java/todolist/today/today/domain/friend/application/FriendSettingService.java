package todolist.today.today.domain.friend.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.friend.dao.CustomFriendRepositoryImpl;
import todolist.today.today.domain.friend.dao.FriendApplyRepository;
import todolist.today.today.domain.friend.dao.FriendRepository;
import todolist.today.today.domain.friend.domain.Friend;
import todolist.today.today.domain.friend.domain.FriendApply;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.domain.User;
import todolist.today.today.domain.user.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendSettingService {

    private final UserRepository userRepository;
    private final CustomFriendRepositoryImpl customFriendRepository;
    private final FriendRepository friendRepository;
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

    public void deleteFriend(String userId, String myId) {
        customFriendRepository.deleteFriend(userId, myId);
    }

    public void makeFriend(String userId, String myId) {
        User user1 = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        User user2 = userRepository.findById(myId)
                .orElseThrow(() -> new UserNotFoundException(myId));

        Friend friend = Friend.builder()
                .friend(user1)
                .user(user2)
                .build();
        friendRepository.save(friend);
    }

}