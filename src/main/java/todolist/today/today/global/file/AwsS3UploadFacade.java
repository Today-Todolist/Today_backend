package todolist.today.today.global.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import todolist.today.today.global.error.exception.file.FileDeleteFailedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class AwsS3UploadFacade implements FileUploadFacade {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    @Override
    public String uploadFile(File file, String fileName) {
        amazonS3.putObject(bucket, fileName, file);
        cleanUp(file.toPath());
        return getFileUrl(fileName);
    }
    
    @Override
    public void deleteFile(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private String getFileUrl(String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void cleanUp(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new FileDeleteFailedException(path.toString());
        }
    }

}
