package todolist.today.today.domain.search.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.search.application.SearchService;
import todolist.today.today.domain.search.dto.response.*;
import todolist.today.today.global.dto.request.PagingRequest;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@RestController @Validated
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/search-word")
    public SearchWordResponse getSearchWord(@RequestParam("word") @Size(min = 1, max = 10) String word) {
        return searchService.getSearchWord(word);
    }

    @GetMapping("/search-amount")
    public SearchAmountResponse getSearchAmount(@RequestParam("word") @Size(min = 1, max = 10) String word) {
        return searchService.getSearchAmount(word);
    }

    @GetMapping("/search-result/nickname") @PreAuthorize("isAuthenticated()")
    public List<NicknameSearchResponse> getNicknameResult(@RequestParam("word") @Size(min = 1, max = 10) String word,
                                                          @RequestParam("page") @Min(0) int page,
                                                          @RequestParam("size") @Positive int size) {
        return searchService.getNicknameResult(authenticationFacade.getUserId(), word, new PagingRequest(page, size));
    }

    @GetMapping("/search-result/email") @PreAuthorize("isAuthenticated()")
    public List<EmailSearchResponse> getEmailResult(@RequestParam("word") @Size(min = 1, max = 10) String word,
                                                    @RequestParam("page") @Min(0) int page,
                                                    @RequestParam("size") @Positive int size) {
        return searchService.getEmailResult(authenticationFacade.getUserId(), word, new PagingRequest(page, size));
    }

    @GetMapping("/search-result/template")
    public List<TemplateSearchResponse> getTemplateResult(@RequestParam("word") @Size(min = 1, max = 10) String word,
                                                          @RequestParam("page") @Min(0) int page,
                                                          @RequestParam("size") @Positive int size) {
        return searchService.getTemplateResult(word, new PagingRequest(page, size));
    }

}
