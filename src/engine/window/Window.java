package engine.window;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

public class Window {
    private JFrame frame;
    private BufferedImage image;
    private BufferStrategy bufferStrategy;
    private Canvas canvas;
    private Graphics graphics;
    private WindowSettings settings;

    public Window(WindowSettings settings) {

        this.settings = settings;

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

        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
    }

    public void setVisible(boolean value){
        frame.setVisible(value);
    }

    private void createCanvas(WindowSettings settings) {

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

    public DataBuffer getImageRasterDataBuffer(){
        return image.getRaster().getDataBuffer();
    }

    public int getWidth(){
        return settings.getWidth();
    }

    public int getHeight(){
        return settings.getHeight();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
