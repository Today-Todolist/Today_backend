package todolist.today.today.infra.file.image


import spock.lang.Specification
import todolist.today.today.infra.file.exception.CreateImageFailedException

import java.lang.reflect.Field

class RandomImageProviderTest extends Specification {

    RandomImageProvider randomImageProvider = new RandomImageProvider()

    def "test createRandomImage" () {
        when:
        File file = randomImageProvider.createRandomImage();

        then:
        file != null
        noExceptionThrown()
        file.delete()
    }

    def "test createRandomImage CreateImageFailedException" () {
        given:
        Random random = Stub(Random)
        Field field = randomImageProvider.getClass().getDeclaredField("random");
        field.setAccessible(true)
        field.set(randomImageProvider, random)
        random.nextBoolean() >> { throw new IOException() }

        when:
        randomImageProvider.createRandomImage()

        then:
        thrown(CreateImageFailedException)
    }

}