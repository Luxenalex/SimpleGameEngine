package engine.input;

public enum NumInputs {
    KEYS (256),
    BUTTONS (5);

    private final int value;

    NumInputs(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
