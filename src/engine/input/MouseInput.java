package engine.input;

import engine.window.WindowSettings;

import java.awt.event.*;
import java.awt.Canvas;

public class MouseInput implements
        MouseListener, MouseMotionListener, MouseWheelListener {

    private WindowSettings windowSettings;

    private boolean[] buttons = new boolean[NumInputs.BUTTONS.getValue()];
    private boolean[] lastButtons = new boolean[NumInputs.BUTTONS.getValue()];

    private int mouseX = 0;
    private int mouseY = 0;
    private int scroll = 0;

    public MouseInput(Canvas canvas, WindowSettings windowSettings) {

        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);

        this.windowSettings = windowSettings;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent event) {
        buttons[event.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        buttons[event.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent event) {
        mouseX = (int) (event.getX() / windowSettings.getScale());
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        mouseX = (int) (event.getX() / windowSettings.getScale());
        mouseY = (int) (event.getY() / windowSettings.getScale());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        scroll = event.getWheelRotation();
    }

    public boolean getButton(int index) {
        return buttons[index];
    }

    public void setLastButton(boolean value, int index) {
        lastButtons[index] = value;
    }

    public int getX() {
        return mouseX;
    }

    public int getY() {
        return mouseY;
    }

    public int getScroll() {
        return scroll;
    }

    public boolean isButton(int index) {
        return buttons[index];
    }

    public boolean isLastButton(int index) {
        return lastButtons[index];
    }

    public void setScroll(int value) {
        this.scroll = value;
    }
}
