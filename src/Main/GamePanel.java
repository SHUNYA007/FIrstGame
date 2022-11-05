package Main;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import static utils.Constants.Direction.*;
import static utils.Constants.PlayerConstants.*;

public class GamePanel extends JPanel {

    private float xdelta = 100, ydelta = 100;
    private BufferedImage bufferedImage ;
    private BufferedImage [][]bufferedImages;
    private MouseInputs mouseInputs;

    private int aniTick =0,aniSpeed = 15,aniIndex=0;

    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean moving = false;
    public GamePanel(){
        mouseInputs = new MouseInputs(this);
        KeyboardInputs keyboardInputs = new KeyboardInputs(this);
//        this.setFocusable(true);
//        this.requestFocus();
        importImage();
        loadAnimations();
        setWindowSize();
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations(){
        bufferedImages = new BufferedImage[9][6];
        for(int i = 0; i<9 ;i++ ){
            for(int j=0; j<6 ;j++){
                    bufferedImages[i][j] = bufferedImage.getSubimage(j * 64, i * 40, 64, 40);
            }
        }
    }
    private void importImage()  {


        try{
            InputStream is = getClass().getResourceAsStream("/player_sprites.png");
            bufferedImage = ImageIO.read(is);

        }
        catch (Exception e){
            System.out.println(e+" :Exception");
        }
    }

    public void setWindowSize(){
        Dimension size = new Dimension(1280,800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void setDirection(int direction){
        this.playerDirection = direction;
        this.setMoving(true);
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }


    public void moveOnMouse(float xdelta, float ydelta) {
        this.ydelta = ydelta;
        this.xdelta = xdelta;
    }
    public BufferedImage getParticularImage(int x, int y){
        return bufferedImage.getSubimage(x*64,y*40,64,40);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        updateAnimation();
        setAnimation();
        updatePos();

        g.drawImage(bufferedImages[playerAction][aniIndex],(int)xdelta,(int)ydelta,128,80,null );
    }

    private void updatePos() {
        if(moving){
            switch (playerDirection){
                case LEFT:
                    xdelta -= 3;
                    break;
                case RIGHT:
                    xdelta += 3;
                    break;
                case UP:
                    ydelta -= 3;
                    break;
                case DOWN:
                    ydelta += 3;
                    break;
            }
        }
    }

    private void setAnimation() {
        if(moving){
            playerAction = RUNNING;
        }
        else
            playerAction = IDLE;
    }

    private void updateAnimation() {
        aniTick++;
        if(aniSpeed<aniTick){
            aniTick = 0;
            aniIndex++;
            aniIndex %= GetSpriteAmount(playerAction);
        }
    }
}
