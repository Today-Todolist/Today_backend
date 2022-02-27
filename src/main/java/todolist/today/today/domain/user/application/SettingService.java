package todolist.today.today.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.domain.User;
import todolist.today.today.domain.user.dto.request.ChangeNicknameRequest;
import todolist.today.today.domain.user.dto.request.ChangePasswordRequest;
import todolist.today.today.domain.user.exception.UserNotFoundException;
import todolist.today.today.infra.file.image.ImageUploadFacade;

@Service
@RequiredArgsConstructor
@Transactional
public class SettingService {

    private final UserRepository userRepository;
    private final ImageUploadFacade imageUploadFacade;
    private final CheckService checkService;

    public void changeProfile(String userId, MultipartFile image) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        String imageUrl = imageUploadFacade.uploadImage(image);
        user.changeProfile(imageUrl);
    }

    public void changeNickname(String userId, ChangeNicknameRequest request) {
        String newNickname = request.getNewNickname();
        checkService.checkNickname(newNickname);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.changeNickname(newNickname);
    }

    public void changePassword(String userId, ChangePasswordRequest request) {
        checkService.checkPassword(userId, request.getPassword());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.changePassword(request.getNewPassword());
    }

    public void changeEditAvailability(String userId, boolean availability) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.changeChangePossible(availability);
    }

}