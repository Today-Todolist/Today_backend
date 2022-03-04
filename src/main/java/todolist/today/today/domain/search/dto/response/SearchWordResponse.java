package todolist.today.today.domain.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchWordResponse {

    private List<String> words;

}
