package structure;

public interface Output {

    /**
     * those are some standard actions that may or may not be supported by implemented classes
     * used in showAction()
     * it helps to activate a state which may not be reproducible from the coherent game object, therefore, it can instead choose an Action
     */
    enum Action {
        STANDARD, SELECTED, UNSELECTED, BORDERLESS, BORDER, OPAQUE, NON_OPAQUE
    }

    /**
     * game can call an Action for specific coordinates to take place
     * @param action = DIE, LIVE, COLOR, UNCOLOR
     */
    void showAction(int x, int y, Action action);

    void showAction(int x, int y, Blueprint blueprint);

    Blueprint getBlueprint(int x, int y);

    void clear();

    void setGame(Game game);

    Game getGame();

    void setSettings(Settings settings);

    int gridHeight();

    int gridWidth();

    void refresh();
}
