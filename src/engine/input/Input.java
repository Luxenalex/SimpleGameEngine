package engine.input;

import engine.window.Window;

public class Input {
    private KeyboardInput keyboard;
    private MouseInput mouse;

public Input(Window window){
    keyboard = new KeyboardInput(window.getCanvas());
    mouse = new MouseInput(window);
    }

    public void update() {
        mouse.setScroll(0);

        for(int i = 0; i < NumInputs.KEYS.getvalue(); i++){

            keyboard.setLastKey(keyboard.isKey(i), i);
        }
        for(int i = 0; i < NumInputs.BUTTONS.getvalue(); i++){
            mouse.setLastButton(mouse.getButton(i), i);
        }
    }

    public boolean isKey(int key) {
        return keyboard.isKey(key);
    }

    public boolean isKeyUp(int key) {
        return !isKey(key) && keyboard.isLastKey(key);
    }

    public boolean isKeyDown(int key) {
        return isKey(key) && !keyboard.isLastKey(key);
    }

    public boolean isButton(int button) {
        return mouse.isButton(button);
    }

    public boolean isButtonUp(int button) {
        return !isButton(button) && mouse.isLastButton(button);
    }

    public boolean isButtonDown(int button) {
        return isButton(button) && !mouse.isLastButton(button);
    }

    public int getScroll() {
        return mouse.getScroll();
    }

    public int getMouseX() {
        return mouse.getX();
    }

    public int getMouseY() {
        return mouse.getY();
    }
}

