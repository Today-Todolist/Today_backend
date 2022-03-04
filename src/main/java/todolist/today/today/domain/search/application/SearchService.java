package todolist.today.today.domain.search.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import todolist.today.today.domain.search.dao.CustomSearchWordRepositoryImpl;
import todolist.today.today.domain.search.dao.SearchWordRepository;
import todolist.today.today.domain.search.domain.SearchWord;
import todolist.today.today.domain.search.dto.response.EmailSearchResponse;
import todolist.today.today.domain.search.dto.response.NicknameSearchResponse;
import todolist.today.today.domain.search.dto.response.SearchAmountResponse;
import todolist.today.today.domain.search.dto.response.SearchWordResponse;
import todolist.today.today.domain.template.dao.TemplateRepository;
import todolist.today.today.domain.user.dao.CustomUserRepositoryImpl;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.global.dto.request.PagingRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final CustomSearchWordRepositoryImpl customSearchWordRepository;
    private final CustomUserRepositoryImpl customUserRepository;
    private final SearchWordRepository searchWordRepository;
    private final UserRepository userRepository;
    private final TemplateRepository templateRepository;

    public SearchWordResponse getSearchWord(String word) {
        return new SearchWordResponse(customSearchWordRepository.getSearchWord(word));
    }

    public SearchAmountResponse getSearchAmount(String word) {
        searchWordRepository.findById(word)
                .orElseGet(() -> new SearchWord(word))
                .addValue();

        long nickname = userRepository.countByNicknameContains(word);
        long email = userRepository.countByEmailContains(word);
        long template = templateRepository.countByTitleContains(word);
        return new SearchAmountResponse(nickname, email, template);
    }

    public List<NicknameSearchResponse> getNicknameResult(String userId, String word, PagingRequest request) {
        return customUserRepository.getNicknameSearchResult(userId, word, request);
    }

    public List<EmailSearchResponse> getEmailResult(String userId, String word, PagingRequest request) {
        return customUserRepository.getEmailSearchResult(userId, word, request);
    }
}
