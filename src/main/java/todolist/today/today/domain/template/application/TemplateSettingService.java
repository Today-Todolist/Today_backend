package todolist.today.today.domain.template.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import todolist.today.today.domain.check.application.CheckService;
import todolist.today.today.domain.template.dao.TemplateRepository;
import todolist.today.today.domain.template.domain.Template;
import todolist.today.today.domain.template.dto.request.TemplateCreateRequest;
import todolist.today.today.domain.template.exception.TemplateNotFoundException;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.domain.User;
import todolist.today.today.domain.user.exception.UserNotFoundException;
import todolist.today.today.infra.file.image.ImageUploadFacade;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateSettingService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final ImageUploadFacade imageUploadFacade;
    private final CheckService checkService;

    public void makeTemplate(String userId, TemplateCreateRequest request) {
        String title = request.getTitle();
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException(userId));

        checkService.checkExistsTemplateTitle(userId, title);
        Template template = Template.builder()
                .user(user)
                .size(request.getLength())
                .title(title)
                .profile(imageUploadFacade.uploadRandomImage())
                .build();
        templateRepository.save(template);
    }

    public void changeProfile(String userId, String templateId, MultipartFile profile) {
        Template template = templateRepository.findById(UUID.fromString(templateId))
                .orElseThrow(() -> new TemplateNotFoundException(templateId));
        if (template.getUser().getEmail().equals(userId)) throw new TemplateNotFoundException(templateId);

        template.updateProfile(imageUploadFacade.uploadImage(profile));
    }

}
