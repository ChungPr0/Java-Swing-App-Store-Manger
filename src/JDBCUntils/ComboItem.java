package JDBCUntils;

public class ComboItem {
    private final String key;
    private final int value;

    public ComboItem(String key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key;
    }

    public int getValue() {
        return value;
    }
}