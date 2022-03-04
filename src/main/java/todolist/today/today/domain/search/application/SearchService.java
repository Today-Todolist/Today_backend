package todolist.today.today.domain.search.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import todolist.today.today.domain.search.dao.CustomSearchRepositoryImpl;
import todolist.today.today.domain.search.dao.SearchWordRepository;
import todolist.today.today.domain.search.domain.SearchWord;
import todolist.today.today.domain.search.dto.response.SearchAmountResponse;
import todolist.today.today.domain.search.dto.response.SearchWordResponse;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final CustomSearchRepositoryImpl customSearchRepository;
    private final SearchWordRepository searchWordRepository;

    public SearchWordResponse getSearchWord(String word) {
        return new SearchWordResponse(customSearchRepository.getSearchWord(word));
    }

    public SearchAmountResponse getSearchAmount(String word) {
        searchWordRepository.findById(word)
                .orElseGet(() -> new SearchWord(word))
                .addValue();
        return customSearchRepository.getSearchAmount(word);
    }

}
