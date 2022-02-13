package todolist.today.today.global.domain;

import lombok.Getter;
import todolist.today.today.domain.friend.domain.id.FriendRelation;
import todolist.today.today.domain.user.domain.User;

import javax.persistence.*;

@Getter
@MappedSuperclass
public class BaseFriend extends BaseCreatedAt {

    @EmbeddedId
    protected FriendRelation id;

    @ManyToOne
    @MapsId("friendEmail")
    @JoinColumn(name = "friend_email")
    protected User friend;

    @ManyToOne
    @MapsId("userEmail")
    @JoinColumn(name = "user_email")
    protected User user;

}
