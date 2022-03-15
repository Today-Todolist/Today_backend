package todolist.today.today.domain.todolist.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.todolist.application.TodolistInfoService;
import todolist.today.today.domain.todolist.dto.response.MyCalendarResponse;
import todolist.today.today.domain.todolist.dto.response.TodolistContentResponse;
import todolist.today.today.domain.todolist.dto.response.TodolistRecordResponse;
import todolist.today.today.domain.todolist.dto.response.UserCalendarResponse;
import todolist.today.today.global.dto.response.PagingResponse;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

import static todolist.today.today.global.dto.LocalDateUtil.convert;

@RestController @Validated
@RequiredArgsConstructor
public class TodolistInfoController {

    private final TodolistInfoService todolistInfoService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/{email}/record")
    public PagingResponse<TodolistRecordResponse> getTodolistRecord(@PathVariable("email") @Email @Size(min = 1, max = 64) String email,
                                                                    @RequestParam("from") @Pattern(regexp = "\\d\\d\\d\\d-\\d\\d-\\d\\d") String startDate,
                                                                    @RequestParam("to") @Pattern(regexp = "\\d\\d\\d\\d-\\d\\d-\\d\\d") String endDate) {
        return todolistInfoService.getTodolistRecord(email, convert(startDate), convert(endDate));
    }

    @GetMapping("/calendar") @PreAuthorize("isAuthenticated()")
    public MyCalendarResponse getMyCalendar(@RequestParam("date") @Pattern(regexp = "\\d\\d\\d\\d-\\d\\d") String date) {
        return todolistInfoService.getMyCalendar(authenticationFacade.getUserId(), date);
    }

    @GetMapping("/{email}/calendar")
    public UserCalendarResponse getUserCalendar(@PathVariable("email") @Email @Size(min = 1, max = 64) String email,
                                                @RequestParam("date") @Pattern(regexp = "\\d\\d\\d\\d-\\d\\d") String date) {
        return todolistInfoService.getUserCalendar(email, date);
    }

    @GetMapping("/todolist") @PreAuthorize("isAuthenticated()")
    public List<TodolistContentResponse> getTodolist(@RequestParam("date") @Pattern(regexp = "\\d\\d\\d\\d-\\d\\d-\\d\\d") String date) {
        return todolistInfoService.getTodolist(authenticationFacade.getUserId(), convert(date));
    }

}
