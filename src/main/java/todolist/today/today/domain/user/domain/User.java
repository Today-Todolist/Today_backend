package todolist.today.today.domain.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import todolist.today.today.domain.friend.domain.Friend;
import todolist.today.today.domain.friend.domain.FriendApply;
import todolist.today.today.domain.template.domain.Template;
import todolist.today.today.domain.todolist.domain.Todolist;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@Entity @Table(name = "USER")
public class User {

    @Id
    @Column(length = 64)
    private String email;

    @Column(columnDefinition = "CHAR(60)", nullable = false)
    private String password;

    @Column(length = 9, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profile;

    private boolean changePossible;

    @Builder
    public User(String email, String password, String nickname, String profile) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profile = profile;
        this.changePossible = false;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeProfile(String newProfile) {
        this.profile = newProfile;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Todolist> todolists;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<FriendApply> sendFriendApplys;

    @OneToMany(mappedBy = "friend", cascade = CascadeType.REMOVE)
    private List<FriendApply> receiveFriendApplys;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Friend> sendFriends;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "friend", cascade = CascadeType.REMOVE)
    private List<Friend> receiveFriends;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Template> templates;

    public List<Friend> getFriends() {
        return Stream.of(sendFriends, receiveFriends)
                .flatMap(Collection::stream)
                .toList();
    }

}
