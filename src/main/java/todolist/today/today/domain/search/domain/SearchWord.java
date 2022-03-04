package todolist.today.today.domain.search.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity @Table(name = "SEARCH_WORD")
public class SearchWord {

    @Id
    @Column(length = 10)
    private String searchWord;

    private int value;

    public SearchWord(String word) {
        this.searchWord = word;
        this.value = 1;
    }

    public void addValue() {
        this.value = value + 1;
    }

}
