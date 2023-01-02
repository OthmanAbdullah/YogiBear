package yogi_bear;

import java.awt.*;
import yogi_bear.Sprite;
import java.util.Random;

public class Ranger extends Sprite {

    private boolean direction; // direction of the Sprite while moving
    private boolean changedirection; // reached obstacle
    private Random rand = new Random();

    public Ranger(int xCoord, int yCoord) {
        super(xCoord, yCoord, "src/resources/rangerImg.png");
        int randInt = rand.nextInt(2) ; 
        changedirection = true;
        if( randInt == 1){
            direction = true; // x direction
        }else{
            direction = false; // y direction
        }
    }

    // Setters
    public void setChangeDirection(boolean f) {
        this.changedirection = f;
    }
    public void setDirection(boolean direction) {
        this.direction = direction;
    }

        // getters
    public boolean isChangeDirection(){
        return changedirection;
   }
    public boolean isInDirectionX() {
        return direction;
    }
    
    
    public Rectangle getBound() {
        if (direction && changedirection) {
            return new Rectangle(this.x, this.y, this.width + GameStaticVariables.RANGER_SIZE, this.height);
        }
        if (direction && !changedirection) {
            return new Rectangle(this.x - 5, this.y, this.width + GameStaticVariables.RANGER_SIZE, this.height);
        }
        if (!direction && changedirection) {
            return new Rectangle(this.x, this.y, this.width, this.height + GameStaticVariables.RANGER_SIZE);
        }
        if (!direction && !changedirection) {
            return new Rectangle(this.x, this.y - 5, this.width, this.height + GameStaticVariables.RANGER_SIZE);
        } else {
            return null;
        }

    }
    
    public void move() {
        if (this.direction) {
            if (this.changedirection) {
                this.x++;
                if (this.x + this.width == GameStaticVariables.FRAME_WIDTH - 20) {
                    this.changedirection = false;
                }
            } else {
                this.x--;
                if (this.x == 0) {
                    this.changedirection = true;
                }
            }
        } else {
            if (this.changedirection) {
                this.y += 1;
                if (this.y + this.height == GameStaticVariables.FRAME_HEIGHT - 60) {
                    this.changedirection = false;
                }
            } else {
                this.y--;
                if (this.y == 0) {
                    this.changedirection = true;
                }
            }
        }
    }

}
