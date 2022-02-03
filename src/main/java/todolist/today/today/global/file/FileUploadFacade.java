package todolist.today.today.global.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadFacade {

    String uploadFile(MultipartFile file);
    void deleteFile(String fileUrl);

}
