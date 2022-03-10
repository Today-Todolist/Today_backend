package todolist.today.today.domain.todolist.api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.todolist.application.TodolistInfoService;
import todolist.today.today.domain.todolist.dto.TodolistRecordResponse;
import todolist.today.today.global.dto.response.PagingResponse;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static todolist.today.today.global.dto.LocalDateUtil.convert;

@RestController @Validated
@RequiredArgsConstructor
public class TodolistInfoController {

    private final TodolistInfoService todolistInfoService;

    @GetMapping("/{email}/record")
    public PagingResponse<TodolistRecordResponse> getTodolistRecord(@PathVariable("email") @Email @Size(min = 1, max = 64) String email,
                                                                    @RequestParam("from") @Pattern(regexp = "\\d\\d\\d\\d-\\d\\d-\\d\\d") String startDate,
                                                                    @RequestParam("to") @Pattern(regexp = "\\d\\d\\d\\d-\\d\\d-\\d\\d") String endDate) {
        return todolistInfoService.getTodolistRecord(email, convert(startDate), convert(endDate));
    }

}
