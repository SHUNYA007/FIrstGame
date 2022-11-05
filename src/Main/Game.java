package Main;

public class Game implements  Runnable{

    Thread gameThread;
    int FPS_SET = 120;
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        startGameLoop();
    }
    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {
        double timePerframe = 1000000000.0/FPS_SET;
        long lastCheckFrame = System.nanoTime();
        long now ;
        long lastCheckMillies = System.currentTimeMillis();
        int frames = 0;
        while(true){
            now = System.nanoTime();
            if(now-lastCheckFrame >= timePerframe){
                gamePanel.repaint();
                lastCheckFrame = System.nanoTime();
                frames ++;
            }
            if (System.currentTimeMillis() - lastCheckMillies >= 1000){
                System.out.println(frames);
                frames = 0;
                lastCheckMillies = System.currentTimeMillis();
            }
        }

    }
}
