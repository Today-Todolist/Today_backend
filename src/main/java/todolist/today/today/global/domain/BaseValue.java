package todolist.today.today.global.domain;

import lombok.Getter;

import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class BaseValue {

    protected int value;

    public void updateValue(int value) {
        this.value = value;
    }

}
