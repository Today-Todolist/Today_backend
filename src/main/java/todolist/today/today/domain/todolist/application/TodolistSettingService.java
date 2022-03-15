package todolist.today.today.domain.todolist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.template.dao.CustomTemplateRepositoryImpl;
import todolist.today.today.domain.todolist.dao.*;
import todolist.today.today.domain.todolist.domain.Todolist;
import todolist.today.today.domain.todolist.domain.TodolistContent;
import todolist.today.today.domain.todolist.domain.TodolistSubject;
import todolist.today.today.domain.todolist.dto.etc.TemplateContentDto;
import todolist.today.today.domain.todolist.dto.etc.content.TemplateContentSubjectContentDto;
import todolist.today.today.domain.todolist.dto.etc.subject.TemplateContentSubjectDto;
import todolist.today.today.domain.todolist.dto.request.TemplateApplyRequest;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.domain.User;
import todolist.today.today.domain.user.exception.UserNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TodolistSettingService {

    private final CustomTemplateRepositoryImpl customTemplateRepository;
    private final UserRepository userRepository;
    private final TodolistRepository todolistRepository;
    private final CustomTodolistSubjectRepositoryImpl customTodolistSubjectRepository;
    private final TodolistSubjectRepository todolistSubjectRepository;
    private final CustomTodolistContentRepositoryImpl customTodolistContentRepository;
    private final TodolistContentRepository todolistContentRepository;
    private final TodolistSortService todolistSortService;

    public void applyTemplate(String userId, LocalDate date, TemplateApplyRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        for (String id : request.getId()) {
            List<TemplateContentDto> templateInfo = customTemplateRepository
                    .getUserTemplateInfo(userId, UUID.fromString(id));

            for (TemplateContentDto templateContentDto : templateInfo) {
                Todolist todolist = todolistRepository.findByUserAndDate(user, date)
                        .orElseGet(() -> Todolist.builder()
                                .user(user)
                                .date(date.plusDays(templateContentDto.getDay()))
                                .build());
                todolist = todolistRepository.save(todolist);

                for (TemplateContentSubjectDto templateContentSubjectDto : templateContentDto.getSubject()) {
                    int sValue = customTodolistSubjectRepository.getTodolistSubjectLastValue(todolist.getTodolistId());
                    if (sValue >= 2147483500) sValue = todolistSortService.sortTodolistSubject(todolist);
                    TodolistSubject subject = TodolistSubject.builder()
                            .todolist(todolist)
                            .subject(templateContentSubjectDto.getSubject())
                            .value(sValue + 100)
                            .build();
                    subject = todolistSubjectRepository.save(subject);

                    for (TemplateContentSubjectContentDto templateContentSubjectContentDto : templateContentSubjectDto.getContent()) {
                        int cValue = customTodolistContentRepository.getTodolistContentLastValue(subject.getTodolistSubjectId());
                        if (cValue >= 2147483500) cValue = todolistSortService.sortTodolistContent(subject);
                        TodolistContent content = TodolistContent.builder()
                                .todolistSubject(subject)
                                .content(templateContentSubjectContentDto.getContent())
                                .value(cValue)
                                .build();
                        todolistContentRepository.save(content);
                    }
                }
            }
        }
    }

}
