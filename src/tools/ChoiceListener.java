package tools;

/**
 * can be applied when multiple when a ChoiceButton/program can have multiple,
 * but not simultaneous, states that can be switched.
 */
public interface ChoiceListener{
    /**
     * should ideally be called only from choice listener
     * @param s -> the state the program/ChoiceButton is being changed to
     */
    void perform(String s);
}
