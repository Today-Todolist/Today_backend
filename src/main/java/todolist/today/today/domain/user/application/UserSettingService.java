package todolist.today.today.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import todolist.today.today.domain.check.application.CheckService;
import todolist.today.today.domain.todolist.dao.TodolistRepository;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.domain.User;
import todolist.today.today.domain.user.dto.request.ChangeNicknameRequest;
import todolist.today.today.domain.user.dto.request.ChangePasswordRequest;
import todolist.today.today.domain.user.dto.request.DeleteUserRequest;
import todolist.today.today.domain.user.dto.request.ResetTodolistRequest;
import todolist.today.today.domain.user.exception.AuthenticationFailedException;
import todolist.today.today.domain.user.exception.UserNotFoundException;
import todolist.today.today.infra.file.image.ImageUploadFacade;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSettingService {

    private final UserRepository userRepository;
    private final TodolistRepository todolistRepository;

    private final ImageUploadFacade imageUploadFacade;
    private final CheckService checkService;

    private final PasswordEncoder passwordEncoder;

    public void changeProfile(String userId, MultipartFile image) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        String imageUrl = imageUploadFacade.uploadImage(image);
        imageUploadFacade.deleteImage(user.getProfile());
        user.changeProfile(imageUrl);
    }

    public void changeNickname(String userId, ChangeNicknameRequest request) {
        String newNickname = request.getNewNickname();
        checkService.checkExistsNickname(newNickname);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.changeNickname(newNickname);
    }

    public void changePassword(String userId, ChangePasswordRequest request) {
        checkService.checkPassword(userId, request.getPassword());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.changePassword(passwordEncoder.encode(request.getPassword()));
    }

    public void changeEditAvailability(String userId, boolean availability) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.changeChangePossible(availability);
    }

    public void resetTodolist(String userId, ResetTodolistRequest request) {
        checkService.checkPassword(userId, request.getPassword());
        todolistRepository.deleteByUserEmail(userId);
    }

    public void deleteUser(String userId, DeleteUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticationFailedException();
        }
        imageUploadFacade.deleteImage(user.getProfile());
        userRepository.deleteById(userId);
    }

}
