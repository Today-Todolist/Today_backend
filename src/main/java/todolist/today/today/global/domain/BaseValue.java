package todolist.today.today.global.domain;

import lombok.Getter;

import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class BaseValue {

    protected int value;

}
