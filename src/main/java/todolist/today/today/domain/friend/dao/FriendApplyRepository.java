package todolist.today.today.domain.friend.dao;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.friend.domain.FriendApply;
import todolist.today.today.domain.friend.domain.id.FriendRelation;

public interface FriendApplyRepository extends CrudRepository<FriendApply, FriendRelation> {

    long countByFriendEmail(String userEmail);
    void deleteByIdUserEmailAndIdFriendEmail(String userEmail, String friendEmail);

}
