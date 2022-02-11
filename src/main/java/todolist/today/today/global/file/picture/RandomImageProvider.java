package todolist.today.today.global.file.picture;

import org.springframework.stereotype.Component;
import todolist.today.today.global.error.exception.file.CreateImageFailException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Component
public class RandomImageProvider {

    public File createRandomImage() {
        try {
            return createRandomImageLogic();
        } catch (IOException e) {
            throw new CreateImageFailException();
        }
    }

    private File createRandomImageLogic() throws IOException {
        final int IMAGE_SIZE = 200;
        final int BLOCK_SIZE = 40;

        BufferedImage bufferedImage = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, IMAGE_SIZE, IMAGE_SIZE);

        graphics.setColor(getRandomColor());
        int x, y;
        for (x = 0; x <= IMAGE_SIZE; x += BLOCK_SIZE)
            for (y = 0; y <= IMAGE_SIZE; y += BLOCK_SIZE)
                if (getRandomBoolean())
                    graphics.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);

        File image = new File(System.getProperty("user.dir") + "/" + UUID.randomUUID() + ".jpg");
        ImageIO.write(bufferedImage, "jpg", image);
        return image;
    }

    private boolean getRandomBoolean() {
        return new Random().nextBoolean();
    }

    private int getRandomInt(int start, int end) {
        return new Random().nextInt(end - start) + start;
    }

    private Color getRandomColor() {
        return new Color(getRandomInt(100, 180), getRandomInt(100, 180), getRandomInt(100, 180));
    }

}