public interface Output {

    enum Action {
        DIE, LIVE, COLOR, UNCOLOR
    }

    /**
     * game can call an Action for specific coordinates to take place
     * @param x
     * @param y
     * @param action = DIE, LIVE, COLOR, UNCOLOR
     */
    public abstract void showAction(int x, int y, Action action);

    public abstract void clear();

    public abstract void setGame(Game game);

    public void setSettings(Settings settings);
}
