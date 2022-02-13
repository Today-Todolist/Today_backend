package todolist.today.today.global.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class BaseTodolistContent extends BaseValue {

    @Column(length = 31, nullable = false)
    protected String content;

}
