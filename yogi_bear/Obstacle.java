package yogi_bear;

import java.awt.*;
import java.util.Random;

public class Obstacle extends Sprite {
    public Obstacle(int xCoord, int yCoord) {
        super(xCoord, yCoord,null);
        Random rand = new Random();
        if(rand.nextInt(2) == 0){
            setImage("src/resources/terrain_block.png");
        }else{
           setImage( "src/resources/obstacle.png");
        }
    }
    public Rectangle getBound(int idx) {
        if (idx == 0) return new Rectangle(this.x + 1000, this.y , this.width + 45, this.height + 45);
        return null;
    }
}
