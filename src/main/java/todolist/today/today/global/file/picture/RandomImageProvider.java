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
        BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = img.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 200, 200);

        graphics.setColor(getRandomColor());
        for (int x = 0; x <= 200; x += 40) {
            for (int y = 0; y <= 200; y += 40) {
                if (getRandomBoolean()) {
                    graphics.fillRect(x, y, 40, 40);
                }
            }
        }

        File file = new File(System.getProperty("user.dir") + "/" + UUID.randomUUID() + ".jpg");
        ImageIO.write(img, "jpg", file);
        return file;
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