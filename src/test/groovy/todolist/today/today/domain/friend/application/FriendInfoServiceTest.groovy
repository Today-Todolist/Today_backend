package todolist.today.today.domain.friend.application

import spock.lang.Specification
import todolist.today.today.domain.friend.dao.CustomFriendApplyRepository
import todolist.today.today.domain.friend.dao.CustomFriendRepository
import todolist.today.today.domain.friend.dao.FriendApplyRepository
import todolist.today.today.domain.friend.dao.FriendRepository
import todolist.today.today.domain.friend.dto.response.UserFriendApplyResponse
import todolist.today.today.domain.friend.dto.response.UserFriendResponse
import todolist.today.today.global.dto.request.PagingRequest
import todolist.today.today.global.dto.response.PagingResponse


class FriendInfoServiceTest extends Specification {

    FriendInfoService friendInfoService
    CustomFriendRepository customFriendRepository = Mock()
    CustomFriendApplyRepository customFriendApplyRepository = Mock()
    FriendRepository friendRepository = Stub()
    FriendApplyRepository friendApplyRepository = Stub()

    def setup() {
        friendInfoService = new FriendInfoService(customFriendRepository,
                customFriendApplyRepository,
                friendRepository,
                friendApplyRepository)
    }

    def "test getUserFriends" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        PagingRequest request = new PagingRequest(0, 1)

        friendRepository.countByFriendEmailOrUserEmail(USER_ID, USER_ID) >> count

        when:
        PagingResponse<UserFriendResponse> response = friendInfoService.getUserFriends(USER_ID, USER_ID, request)

        then:
        number * customFriendRepository.getUserFriends(USER_ID, USER_ID, request)
        response.getTotalElements() == count

        where:
        count || number
        0L || 0
        1L || 1
    }

    def "test getUserFriendsApply" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        PagingRequest request = new PagingRequest(0, 1)

        friendApplyRepository.countByFriendEmail(USER_ID) >> count

        when:
        PagingResponse<UserFriendApplyResponse> response = friendInfoService.getUserFriendsApply(USER_ID, request)

        then:
        number * customFriendApplyRepository.getUserFriendApply(USER_ID, request)
        response.getTotalElements() == count

        where:
        count || number
        0L || 0
        1L || 1
    }

}