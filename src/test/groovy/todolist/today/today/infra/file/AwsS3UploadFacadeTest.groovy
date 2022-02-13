package todolist.today.today.infra.file

import com.amazonaws.services.s3.AmazonS3
import spock.lang.Specification
import todolist.today.today.infra.file.exception.FileDeleteFailedException

import java.lang.reflect.Field

class AwsS3UploadFacadeTest extends Specification {

    private AwsS3UploadFacade awsS3UploadFacade
    private AmazonS3 amazonS3 = Mock(AmazonS3)
    private final BUCKET = "bucket"

    def setup() {
        awsS3UploadFacade = new AwsS3UploadFacade(amazonS3)
        Field field = awsS3UploadFacade.getClass().getDeclaredField("bucket")
        field.setAccessible(true)
        field.set(awsS3UploadFacade, BUCKET)
    }

    def "test uploadFile" () {
        given:
        final String FILENAME = "testFile"
        final String FILEURL = "https://testUrl"
        amazonS3.getUrl(BUCKET, FILENAME) >> new URL(FILEURL)
        File file = new File(System.getProperty("user.dir") + "/test.jpg")
        file.createNewFile()

        when:
        String s3Url = awsS3UploadFacade.uploadFile(file, FILENAME)

        then:
        s3Url == FILEURL
    }

    def "test uploadFile throw FileDeleteFailedException" () {
        given:
        final String FILENAME = "testFile"
        final String FILEURL = "https://testUrl"
        amazonS3.getUrl(BUCKET, FILENAME) >> new URL(FILEURL)
        File file = new File(System.getProperty("user.dir") + "/test.jpg")

        when:
        awsS3UploadFacade.uploadFile(file, FILENAME)

        then:
        thrown(FileDeleteFailedException)
    }

    def "test deleteFile" () {
        given:
        final String fileUrl = "https://test/test.jpg"

        when:
        awsS3UploadFacade.deleteFile(fileUrl)

        then:
        1 * amazonS3.deleteObject(_)
    }

}