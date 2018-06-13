package input;

public enum NumInputs {
    KEYS (256),
    BUTTONS (5);

    private final int value;

    private NumInputs(int value) {
        this.value = value;
    }

    public int getvalue() {
        return this.value;
    }
}
