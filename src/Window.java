import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {
    private JFrame frame;
    private BufferedImage image;
    private BufferStrategy bufferStrategy;
    private Canvas canvas;
    private Graphics graphics;

    public Window(WindowSettings settings) {

        image = new BufferedImage(
                settings.getWidth(),
                settings.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        createCanvas(settings);

        bufferStrategy = canvas.getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();
    }

    private void createFrame(WindowSettings settings) {
        frame = new JFrame(settings.getTitle());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
    }

    public void createCanvas(WindowSettings settings) {

        canvas = new Canvas();
        Dimension size = new Dimension(
                (int)(settings.getWidth() * settings.getScale()),
                (int)(settings.getHeight() * settings.getScale())
        );

        canvas.setPreferredSize(size);
        canvas.setMinimumSize(size);
        canvas.setMaximumSize(size);

        createFrame(settings);
        canvas.createBufferStrategy(2);
    }

    public void update(){
        graphics.drawImage(image, 0, 0, canvas.getWidth(),
                           canvas.getHeight(), null);
        bufferStrategy.show();
    }
}
