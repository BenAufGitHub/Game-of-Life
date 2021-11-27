package graphics;

public interface GUIRunnable {
    public void run() throws InterruptedException;
    public void act();
    public void stop();
    public void forceStop();
    public boolean noProcess();
}
