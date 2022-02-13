package todolist.today.today.infra.file;

import java.io.File;

public interface FileUploadFacade {

    String uploadFile(File file, String fileName);
    void deleteFile(String fileUrl);

}
