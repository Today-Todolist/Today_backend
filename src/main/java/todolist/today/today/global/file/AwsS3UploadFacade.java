package todolist.today.today.global.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import todolist.today.today.global.error.exception.file.FileSaveFailedException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AwsS3UploadFacade implements FileUploadFacade {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new FileSaveFailedException(e);
        }

        amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return getFileUrl(fileName);
    }
    
    @Override
    public void deleteFile(String fileUrl) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, getFileName(fileUrl)));
    }

    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(originalFileName);
    }

    private String getFileUrl(String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private String getFileName(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }

}
