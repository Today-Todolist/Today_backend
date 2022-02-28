package todolist.today.today.domain.friend.dao;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.friend.domain.Friend;
import todolist.today.today.domain.friend.domain.id.FriendRelation;

public interface FriendRepository extends CrudRepository<Friend, FriendRelation> {
}
