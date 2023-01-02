package yogi_bear;
import java.awt.event.KeyEvent;

public class Yogi extends Sprite{
    private int vX;
    private int vY;
    public Yogi(int x, int y) {super(x, y, "src/resources/yogi.png");}
    public void move(){
        this.x += this.vX;
        this.y += this.vY;

        if (this.x < 1 ) {
            this.x = 1;
        }
        if (this.y < 1) {
            this.y = 1;
        }
        if((this.y + this.height) > GameStaticVariables.FRAME_HEIGHT - 10){
             this.y = (GameStaticVariables.FRAME_HEIGHT - this.height - 10);
        }

        if (this.x + this.width > GameStaticVariables.FRAME_WIDTH - 22 ){
            this.x = (GameStaticVariables.FRAME_WIDTH - this.width - 22);
        }
    }

    // GETTERS
    public int getDx() {return vX;}
    public int getDy(){return vY;}

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch(key){
            case  KeyEvent.VK_A:
                this.vX = - GameStaticVariables.YOGI_NUM_OF_STEPS_PER_PRESSED_KEY;
                break;
            case KeyEvent.VK_D:
                  this.vX = GameStaticVariables.YOGI_NUM_OF_STEPS_PER_PRESSED_KEY;
                break;
            case KeyEvent.VK_W:
                this.vY = - GameStaticVariables.YOGI_NUM_OF_STEPS_PER_PRESSED_KEY;
                break;
            case KeyEvent.VK_S:
               this.vY = GameStaticVariables.YOGI_NUM_OF_STEPS_PER_PRESSED_KEY;
                break;   
        }
    }
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_D ) {
            this.vX = 0;
        }
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
            this.vY = 0;
        }
       
    }
}
