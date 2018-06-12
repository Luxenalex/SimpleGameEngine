package input;

import java.awt.Canvas;

public class Input {
    private KeyboardInput keyboard;
    private MouseInput mouse;

public Input(Canvas canvas){
    keyboard = new KeyboardInput(canvas);
    mouse = new MouseInput(canvas);
    }

    public void update() {


    }
}

