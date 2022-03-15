package todolist.today.today.infra.file.image

import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification
import todolist.today.today.infra.file.FileUploadFacade
import todolist.today.today.infra.file.exception.FileSaveFailedException
import todolist.today.today.infra.file.exception.WrongImageContentTypeException
import todolist.today.today.infra.file.exception.WrongImageExtensionException

class ImageUploadFacadeTest extends Specification {

    ImageUploadFacade imageUploadFacade
    FileUploadFacade fileUploadFacade = Mock()
    RandomImageProvider randomImageProvider = Mock()

    def setup() {
        imageUploadFacade = new ImageUploadFacade(fileUploadFacade, randomImageProvider)
    }

    def "test uploadImage" () {
        given:
        MultipartFile multipartFile = Stub()
        multipartFile.getOriginalFilename() >> "test.jpeg"
        multipartFile.getContentType() >> "image/png"

        when:
        imageUploadFacade.uploadImage(multipartFile)

        then:
        1 * fileUploadFacade.uploadFile(_, _)
    }

    def "test uploadImage WrongImageException" () {
        given:
        MultipartFile multipartFile = Stub()
        multipartFile.getOriginalFilename() >> fileName
        multipartFile.getContentType() >> contentType

        when:
        imageUploadFacade.uploadImage(multipartFile)

        then:
        thrown(exception)

        where:
        fileName | contentType || exception
        "test.svg" | "image/svg+xml" || WrongImageExtensionException
        "test.png" | "image/svg+xml" || WrongImageContentTypeException
    }

    def "test uploadImage FileSaveFailedException" () {
        given:
        MultipartFile multipartFile = Stub()
        multipartFile.getOriginalFilename() >> "test.jpeg"
        multipartFile.getContentType() >> "image/png"
        multipartFile.transferTo(_) >> { throw new IOException() }

        when:
        imageUploadFacade.uploadImage(multipartFile)

        then:
        thrown(FileSaveFailedException)
    }

    def "test uploadRandomImage" () {
        when:
        imageUploadFacade.uploadRandomImage()

        then:
        1 * fileUploadFacade.uploadFile(_, _)
    }

    def "test deleteImage" () {
        given:
        final String FILEURL = "testUrl"

        when:
        imageUploadFacade.deleteImage(FILEURL)

        then:
        1 * fileUploadFacade.deleteFile(FILEURL)
    }

}