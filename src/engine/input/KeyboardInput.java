package engine.input;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by lux on 6/12/18.
 */
public class KeyboardInput implements KeyListener {
    private boolean[] keys = new boolean[NumInputs.KEYS.getvalue()];
    private boolean[] lastKeys = new boolean[NumInputs.KEYS.getvalue()];

    public KeyboardInput(Canvas canvas) {
        canvas.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent event) {
        keys[event.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent event) {
        keys[event.getKeyCode()] = false;
    }

    public boolean isKey(int index) {
        return keys[index];
    }

    public void setLastKey(boolean value, int index) {
        lastKeys[index] = value;
    }

    public boolean isLastKey(int index) {
        return lastKeys[index];
    }
}
