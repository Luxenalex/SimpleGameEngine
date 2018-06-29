package engine;

import engine.window.Window;
import engine.window.WindowSettings;
import org.junit.*;

public class RendererTest {

    Renderer renderer;
    Window window;

    @Before
    public void setup(){
        window = new Window(new WindowSettings());
        renderer = new Renderer(window);
    }

    @Test
    public void setPixelShouldMakeWindowGreen(){

        window.setVisible(true);


        for(int y = 0; y < window.getHeight(); y++) {
            for(int x = 0; x < window.getWidth(); x++) {
                renderer.setPixel(x, y, 0xFF088A08, 0);
            }
        }

        window.update();
        renderer.clear();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
