package todolist.today.today.domain.search.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import todolist.today.today.domain.search.dao.CustomSearchRepositoryImpl;
import todolist.today.today.domain.search.dto.response.SearchWordResponse;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final CustomSearchRepositoryImpl customSearchRepository;

    public SearchWordResponse getSearchWord(String word) {
        return new SearchWordResponse(customSearchRepository.getSearchWord(word));
    }

}
