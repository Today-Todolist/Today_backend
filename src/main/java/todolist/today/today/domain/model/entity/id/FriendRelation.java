package todolist.today.today.domain.model.entity.id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@NoArgsConstructor @AllArgsConstructor
@Embeddable
public class FriendRelation implements Serializable {

    @Column(length = 64, nullable = false)
    private String friendEmail;

    @Column(length = 64, nullable = false)
    private String userEmail;

}
