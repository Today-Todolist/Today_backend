package todolist.today.today.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.domain.User;
import todolist.today.today.domain.user.exception.UserNotFoundException;
import todolist.today.today.infra.file.image.ImageUploadFacade;

@Service
@RequiredArgsConstructor
@Transactional
public class SettingService {

    private final UserRepository userRepository;
    private final ImageUploadFacade imageUploadFacade;

    public void changeProfile(MultipartFile image, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        String imageUrl = imageUploadFacade.uploadImage(image);
        user.changeProfile(imageUrl);
    }

}
