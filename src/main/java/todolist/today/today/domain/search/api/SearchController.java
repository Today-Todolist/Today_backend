package todolist.today.today.domain.search.api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.search.application.SearchService;
import todolist.today.today.domain.search.dto.response.SearchWordResponse;

import javax.validation.constraints.Size;

@RestController @Validated
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search-word")
    public SearchWordResponse getSearchWord(@RequestParam("word") @Size(min = 1, max = 10) String word) {
        return searchService.getSearchWord(word);
    }

}
