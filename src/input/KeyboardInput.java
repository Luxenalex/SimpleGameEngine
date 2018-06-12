package input;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by lux on 6/12/18.
 */
public class KeyboardInput implements KeyListener {
    private boolean[] keys = new boolean[NumInputs.KEYS.getvalue()];
    private boolean[] keysLast = new boolean[NumInputs.KEYS.getvalue()];

    public KeyboardInput(Canvas canvas) {
        canvas.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public boolean getKeys(int index) {
        return keys[index];
    }
}
