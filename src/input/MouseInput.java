package input;

import java.awt.event.*;
import java.awt.Canvas;

/**
 * Created by lux on 6/12/18.
 */
public class MouseInput implements
        MouseListener, MouseMotionListener, MouseWheelListener {

    private boolean[] buttons = new boolean[NumInputs.BUTTONS.getvalue()];
    private boolean[] buttonsLast = new boolean[NumInputs.BUTTONS.getvalue()];

    private int mouseX = 0;
    private int mouseY = 0;
    private int scroll = 0;

    public MouseInput(Canvas canvas) {
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }
}
