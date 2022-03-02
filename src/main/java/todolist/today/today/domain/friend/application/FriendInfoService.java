package todolist.today.today.domain.friend.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.friend.dao.CustomFriendApplyRepositoryImpl;
import todolist.today.today.domain.friend.dao.CustomFriendRepositoryImpl;
import todolist.today.today.domain.friend.dao.FriendApplyRepository;
import todolist.today.today.domain.friend.dao.FriendRepository;
import todolist.today.today.domain.friend.dto.response.UserFriendApplyResponse;
import todolist.today.today.domain.friend.dto.response.UserFriendResponse;
import todolist.today.today.global.dto.request.PagingRequest;
import todolist.today.today.global.dto.response.PagingResponse;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendInfoService {

    private final CustomFriendRepositoryImpl customFriendRepository;
    private final CustomFriendApplyRepositoryImpl customFriendApplyRepository;
    private final FriendRepository friendRepository;
    private final FriendApplyRepository friendApplyRepository;

    public PagingResponse<UserFriendResponse> getUserFriends(String userId, String myId, PagingRequest request) {
        long totalElements = friendRepository.countByFriendEmailOrUserEmail(userId, userId);

        List<UserFriendResponse> response;
        if (totalElements >= 1) response = customFriendRepository.getUserFriends(userId, myId, request);
        else response = Collections.emptyList();

        return new PagingResponse<>(totalElements, response);
    }

    public PagingResponse<UserFriendApplyResponse> getUserFriendsApply(String userId, PagingRequest request) {
        long totalElements = friendApplyRepository.countByFriendEmailOrUserEmail(userId, userId);

        List<UserFriendApplyResponse> response;
        if (totalElements >= 1) response = customFriendApplyRepository.getUserFriendApply(userId, request);
        else response = Collections.emptyList();

        return new PagingResponse<>(totalElements, response);
    }

}