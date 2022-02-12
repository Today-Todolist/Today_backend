package todolist.today.today.domain.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import todolist.today.today.domain.model.entity.id.FriendRelation;
import todolist.today.today.global.jpa.entity.BaseCreatedAt;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity @Table(name = "FRIEND_APPLY")
public class FriendApply extends BaseCreatedAt {

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
    public FriendApply(User friend, User user) {
        this.id = new FriendRelation(friend.getEmail(), user.getEmail());
        this.friend = friend;
        this.user = user;
    }

}
