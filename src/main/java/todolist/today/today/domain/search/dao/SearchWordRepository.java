package todolist.today.today.domain.search.dao;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.search.domain.SearchWord;

public interface SearchWordRepository extends CrudRepository<SearchWord, String> {
}
