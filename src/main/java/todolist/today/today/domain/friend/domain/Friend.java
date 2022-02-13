package todolist.today.today.domain.friend.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import todolist.today.today.domain.friend.domain.id.FriendRelation;
import todolist.today.today.domain.user.domain.User;
import todolist.today.today.global.domain.BaseCreatedAt;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity @Table(name = "FRIEND")
public class Friend extends BaseCreatedAt {

    @EmbeddedId
    private FriendRelation id;

    @ManyToOne
    @MapsId("friendEmail")
    @JoinColumn(name = "friend_email")
    private User friend;

    @ManyToOne
    @MapsId("userEmail")
    @JoinColumn(name = "user_email")
    private User user;

    @Builder
    public Friend(User friend, User user) {
        this.id = new FriendRelation(friend.getEmail(), user.getEmail());
        this.friend = friend;
        this.user = user;
    }

}
