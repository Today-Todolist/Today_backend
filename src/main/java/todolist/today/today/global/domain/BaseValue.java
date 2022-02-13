package todolist.today.today.global.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@Getter
@SuperBuilder @NoArgsConstructor
@MappedSuperclass
public abstract class BaseValue {

    protected int value;

}
