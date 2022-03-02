package todolist.today.today.global.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PagingRequest {

    private int page;
    private int size;

}
