package todolist.today.today.global.file.picture;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import todolist.today.today.global.error.exception.file.WrongImageContentTypeException;
import todolist.today.today.global.error.exception.file.WrongImageExtensionException;
import todolist.today.today.global.file.FileUploadFacade;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageUploadFacade {

    private final FileUploadFacade fileUploadFacade;
    private final RandomImageProvider randomImageProvider;

    private final List<String> IMAGE_EXTENSION = Arrays.asList("jpeg", "png", "jpg");
    private final List<String> IMAGE_CONTENT_TYPE = Arrays.asList("image/png", "image/jpeg");

    public String uploadImage(MultipartFile requestImage) {
        String extension = FilenameUtils.getExtension(requestImage.getOriginalFilename());
        String contentType = requestImage.getContentType();

        if (!IMAGE_EXTENSION.contains(extension)) {
            throw new WrongImageExtensionException(extension);
        } else if(!IMAGE_CONTENT_TYPE.contains(contentType)) {
            throw new WrongImageContentTypeException(contentType);
        }

        File image = new File(System.getProperty("user.dir") + "/" + UUID.randomUUID() + "." + extension);
        return fileUploadFacade.uploadFile(image, createFileName(extension));
    }

    public String uploadRandomImage() {
        File image = randomImageProvider.createRandomImage();
        return fileUploadFacade.uploadFile(image, createFileName("jpg"));
    }

    public void deleteImage(String imageUrl) {
        fileUploadFacade.deleteFile(imageUrl);
    }

    private String createFileName(String extension) {
        return "Image_" + UUID.randomUUID().toString().concat("." + extension);
    }

}
