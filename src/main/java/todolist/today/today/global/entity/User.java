package todolist.today.today.global.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Builder @NoArgsConstructor @AllArgsConstructor
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

}
