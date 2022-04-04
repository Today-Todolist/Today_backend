package todolist.today.today.domain.search.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class SearchWord {

    @Id
    @Column(length = 10)
    private String searchWord;

    private int value;

    public SearchWord(String word) {
        this.searchWord = word;
        this.value = 0;
    }

    public void addValue() {
        this.value = value + 1;
    }

}
