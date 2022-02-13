package todolist.today.today.domain.friend.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import todolist.today.today.domain.friend.domain.id.FriendRelation;
import todolist.today.today.domain.user.domain.User;
import todolist.today.today.global.domain.BaseFriend;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity @Table(name = "FRIEND_APPLY")
public class FriendApply extends BaseFriend {

    @Builder
    public FriendApply(User friend, User user) {
        this.id = new FriendRelation(friend.getEmail(), user.getEmail());
        this.friend = friend;
        this.user = user;
    }

}
