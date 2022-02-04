package todolist.today.today.global.file.picture;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import todolist.today.today.global.error.exception.file.WrongImageContentTypeException;
import todolist.today.today.global.error.exception.file.WrongImageExtensionException;
import todolist.today.today.global.file.FileUploadFacade;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ImageUploadFacade {

    private final FileUploadFacade fileUploadFacade;

    private final List<String> IMAGE_EXTENSION = Arrays.asList("jpeg", "png", "jpg");
    private final List<String> IMAGE_CONTENT_TYPE = Arrays.asList("image/png", "image/jpeg");

    public String uploadImage(MultipartFile picture) {
        String extension = FilenameUtils.getExtension(picture.getOriginalFilename());
        String contentType = picture.getContentType();

        if (!IMAGE_EXTENSION.contains(extension)) {
            throw new WrongImageExtensionException(extension);
        } else if(!IMAGE_CONTENT_TYPE.contains(contentType)) {
            throw new WrongImageContentTypeException(contentType);
        }

        return fileUploadFacade.uploadFile(picture);
    }

    public void deleteImage(String pictureUrl) {
        fileUploadFacade.deleteFile(pictureUrl);
    }

}
