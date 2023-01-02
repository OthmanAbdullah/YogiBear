package yogi_bear;

import java.awt.*;
import java.util.Random;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.*;

public class GameEngine extends JPanel implements ActionListener {

    private int score;
    private int level;
    private int yogiNumOfLifes;
    private int numOfRangers;
    private int numOfMountainsAndTrees;
    private int numOfBaskets;
    private boolean isAlive;

    private Yogi yogi;
    private YogiBearGui mainframe;

    private Timer timer;
    private long timeStart;
    private long currentTime;

    private List<Basket> baskets;
    private List<Ranger> rangers;
    private List<Obstacle> obstacles;

    Random random = new Random();
    private final Rectangle yoigStartArea = new Rectangle(GameStaticVariables.YOGI_START_X_COORD, GameStaticVariables.YOGI_START_Y_COORD, 50, 70);

    public GameEngine(YogiBearGui frame) {
        mainframe = frame;
        level = 1;
        score = 0;
        yogiNumOfLifes = 3;
        numOfRangers = 1;
        numOfMountainsAndTrees = 1;
        numOfBaskets = 1;
        isAlive = true;
        yogi = new Yogi(GameStaticVariables.YOGI_START_X_COORD, GameStaticVariables.YOGI_START_Y_COORD);

        setFocusable(true);
        setDoubleBuffered(true);
        setPreferredSize(new Dimension(GameStaticVariables.FRAME_WIDTH, GameStaticVariables.FRAME_HEIGHT));
        addKeyListener(new TAdapter());

        placeGameObjects();

        timeStart = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();

        timer = new Timer(GameStaticVariables.DELAY, this);
        timer.start();
    }

    private void placeGameObjects() {
        placeObstacles();
        placeBaskets();
        placeRangers();
    }

    private void restart(){
        mainframe.gameFrame.dispose();
        mainframe.updateGameFrame();
        mainframe.gameFrame.setVisible(true);
    }
    private void nextLevel() {
        if (level == 10) {
            isAlive = false;
            String pName = JOptionPane.showInputDialog(this, "Enter your name: ", "Congrats You won!", JOptionPane.INFORMATION_MESSAGE);
            if (pName != null) {
                try {
                    new yogi_bear.Database(0).putHighScore(pName, score);
                } catch (SQLException e) {
                    Logger.getLogger(GameEngine.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
                }
            }
            int choice = JOptionPane.showConfirmDialog(this, "Do You Want To Restart?", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
            if (choice == JOptionPane.OK_OPTION){
                restart();
            } else {
                System.exit(0);
            }
        } else {
            numOfRangers++;
            numOfMountainsAndTrees += 2;
            numOfBaskets += 2;
            yogi.x = GameStaticVariables.YOGI_START_X_COORD;
            yogi.y = GameStaticVariables.YOGI_START_Y_COORD;
            placeGameObjects();
            level++;
        }
    }

    private void placeBaskets() {
        baskets = new ArrayList<>();
        baskets.add(new Basket(random.nextInt(GameStaticVariables.FRAME_WIDTH - 130) + 40, random.nextInt(GameStaticVariables.FRAME_HEIGHT - 150) + 40));
//        placeGameObjectsOnFreePlaces(baskets,numOfBaskets,'B');
        for (int i = 0; i < numOfBaskets; i++) {
            boolean isPlacedSuccessfully = false;
            while (!isPlacedSuccessfully) {
                Basket tmpBasket = new Basket(random.nextInt(GameStaticVariables.FRAME_WIDTH - 130) + 40, random.nextInt(GameStaticVariables.FRAME_HEIGHT - 150) + 40);
                Rectangle tmpBasketContainer = tmpBasket.getBounds();
                boolean successInsertion = true;

                for (int j = 0; j < obstacles.size() && successInsertion; j++) {
                    Obstacle possibleBlockingObstacle = obstacles.get(j);
                    Rectangle possibleBlockingObstacleContainer = possibleBlockingObstacle.getBounds();
                    if ((possibleBlockingObstacleContainer.contains(tmpBasketContainer)) || (possibleBlockingObstacleContainer.intersects(tmpBasketContainer)) || tmpBasketContainer.contains(yoigStartArea) || tmpBasketContainer.intersects(yoigStartArea)) {
                        successInsertion = false;
                    }
                }

                if (successInsertion) {
                    baskets.add(tmpBasket);
                    isPlacedSuccessfully = true;
                }
            }
        }
    }

    private void placeRangers() {
        rangers = new ArrayList<>();
        rangers.add(new Ranger(random.nextInt(GameStaticVariables.FRAME_WIDTH - 130) + 40, random.nextInt(GameStaticVariables.FRAME_HEIGHT - 150) + 40));
//        placeGameObjectsOnFreePlaces(rangers,numOfRangers,'R');
        for (int i = 0; i < numOfRangers; i++) {
            boolean isPlacedSuccessfully = false;
            while (!isPlacedSuccessfully) {
                Ranger temporaryRanger = new Ranger(random.nextInt(GameStaticVariables.FRAME_WIDTH - 130) + 40, random.nextInt(GameStaticVariables.FRAME_HEIGHT - 150) + 40);
                Rectangle temporaryRangerRect = temporaryRanger.getBounds();
                boolean successInsertion = true;

                for (int j = 0; j < obstacles.size() && successInsertion; j++) {
                    Obstacle possibleBlockingObstacle = obstacles.get(j);
                    Rectangle possibleBlockingObstacleContainer = possibleBlockingObstacle.getBounds();
                    if ((possibleBlockingObstacleContainer.contains(temporaryRangerRect)) || (possibleBlockingObstacleContainer.intersects(temporaryRangerRect)) || temporaryRangerRect.contains(yoigStartArea) || temporaryRangerRect.intersects(yoigStartArea)) {
                        successInsertion = false;
                    }
                }
                if (successInsertion) {
                    rangers.add(temporaryRanger);
                    isPlacedSuccessfully = true;
                }
            }

        }
    }

    private void updateGameObjectsState() {
        if (!isAlive) {
            timer.stop();
        }
        if (yogi.isVisible()) {
            yogi.move();
        }
        rangers.forEach(rn -> rn.move());
        if (baskets.isEmpty()) {
            nextLevel();
        } else {
            for (int i = 0; i < baskets.size(); i++) {
                Basket b = baskets.get(i);
                if (!b.isVisible()) {
                    baskets.remove(i);
                }
            }
        }
    }

    private void placeGameObjectsOnFreePlaces(List<?> objects, int numOfObjects, char type) {
        Random rand = new Random();
        boolean isPlacedSuccessfully;
        Rectangle container;

        for (int i = 1; i < numOfObjects; i++) {
//            Obstacle obstacle =  null;
            Ranger ranger = null;
            Basket basket = null;
            isPlacedSuccessfully = false;
            while (!isPlacedSuccessfully) {
//                if(type == 'O'){
//                    obstacle = new Obstacle(rand.nextInt(GameStaticVariables.FRAME_WIDTH - 130) + 40, rand.nextInt(GameStaticVariables.FRAME_HEIGHT - 150) + 40);
//                    container = obstacle.getBound(0);
                if (type == 'R') {
                    ranger = new Ranger(rand.nextInt(GameStaticVariables.FRAME_WIDTH - 130) + 40, rand.nextInt(GameStaticVariables.FRAME_HEIGHT - 150) + 40);
                    container = ranger.getBounds();
                } else {
                    basket = new Basket(rand.nextInt(GameStaticVariables.FRAME_WIDTH - 130) + 40, rand.nextInt(GameStaticVariables.FRAME_HEIGHT - 150) + 40);
                    container = basket.getBounds();
                }

                boolean success = true;
                int j = 0;
                // make sure to not have collisions
                while (j < objects.size() && success) {
                    Obstacle obstacle1 = obstacles.get(j);
                    Rectangle obstacleContainer = null;
//                    if(type == 'O'){
//                        obstacleContainer = obstacle1.getBound(0);
//                   }else{
                    obstacleContainer = obstacle1.getBounds();
//                   }

                    if ((obstacleContainer.contains(container)) || (obstacleContainer.intersects(container)) || container.contains(yoigStartArea) || container.intersects(yoigStartArea)) {
                        success = false;
                    }
                    j++;
                }
                if (success) {
//                    if(type == 'O'){
//                        obstacles.add(obstacle);
                    if (type == 'R') {
                        rangers.add(ranger);
                    } else {
                        baskets.add(basket);
                    }
//                    obstacles.add(tmpTerrain);
                    isPlacedSuccessfully = true;
                }
            }
        }
    }

    private void placeObstacles() {
        obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(random.nextInt(GameStaticVariables.FRAME_WIDTH - 130) + 40, random.nextInt(GameStaticVariables.FRAME_HEIGHT - 150) + 40));
        boolean isPlacedSuccessfully = false;
        for (int i = 1; i < numOfMountainsAndTrees; i++) {
            isPlacedSuccessfully = false;
            while (!isPlacedSuccessfully) {
                Obstacle temporaryObstacle = new Obstacle(random.nextInt(GameStaticVariables.FRAME_WIDTH - 130) + 40, random.nextInt(GameStaticVariables.FRAME_HEIGHT - 150) + 40);
                Rectangle obstContainer = temporaryObstacle.getBound(0);
                boolean successInsertion = true;

                for (int j = 0; j < obstacles.size() && successInsertion; j++) {
                    Obstacle possibleBlockingObstacle = obstacles.get(j);
                    Rectangle possibleBlockingObstacleContainer = possibleBlockingObstacle.getBound(0);
                    if ((possibleBlockingObstacleContainer.contains(obstContainer)) || (possibleBlockingObstacleContainer.intersects(obstContainer)) || obstContainer.contains(yoigStartArea) || obstContainer.intersects(yoigStartArea)) {
                        successInsertion = false;
                    }
                }
                if (successInsertion) {
                    obstacles.add(temporaryObstacle);
                    isPlacedSuccessfully = true;
                }
            }

        }
//        placeGameObjectsOnFreePlaces(obstacles, numOfMountainsAndTrees,'O');
    }

    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        ImageIcon img = new ImageIcon("src/resources/bgImage.jpg");
        Image backgroundImg = img.getImage();
//        gr.drawImage(backgroundImg, 0, 0, null);
        gr.drawImage(backgroundImg, 0, 0, GameStaticVariables.FRAME_WIDTH  , GameStaticVariables.FRAME_HEIGHT , null);
        if (isAlive) {
            drawObjects(gr);
        } else {
            String pName = JOptionPane.showInputDialog(this, "Enter your name: ", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
            if (pName != null) {
                try {
                    new yogi_bear.Database(0).putHighScore(pName, score);
                } catch (SQLException e) {
                    Logger.getLogger(GameEngine.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
                }
            }
            int choice = JOptionPane.showConfirmDialog(this, "Restart?", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
            if (choice == JOptionPane.OK_OPTION) {
                restart();
            } else {
                System.exit(0);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics gr) {
        if (yogi.isVisible()) {
            gr.drawImage(yogi.getImage(), yogi.getX(), yogi.getY(), this);
        }
        baskets.forEach(
                b -> {
                    if (b.isVisible()) {
                        gr.drawImage(b.getImage(), b.getX(), b.getY(), this);
                    }
                });
        rangers.forEach(
                r -> {
                    if (r.isVisible()) {
                        gr.drawImage(r.getImage(), r.getX(), r.getY(), this);
                    }
                });
        obstacles.forEach(
                o -> {
                    if (o.isVisible()) {
                        gr.drawImage(o.getImage(), o.getX(), o.getY(), this);
                    }
                });
        gr.setColor(Color.BLACK);
        currentTime = System.currentTimeMillis();
        gr.drawString("Lives: " + yogiNumOfLifes, 15, 15);
        gr.drawString("score: " + score, 800, 16);
        gr.drawString("Level: " + level, 978, 16);
        gr.drawString("Elapsed Time: " + (int) ((currentTime - timeStart) / 1000), 1350, 16);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        updateGameObjectsState();
        try {
            checkCollisions();
        } catch (SQLException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        repaint();
    }

    private void checkCollisions() throws SQLException {
        // baskets
        Rectangle yogiRect = yogi.getBounds();
        for (int i = 0; i < baskets.size(); i++) {
            Basket b = baskets.get(i);
            Rectangle bRect = b.getBounds();
            if (yogiRect.intersects(bRect)) {
                b.setVisible(false);
                score += 1;
            }
        }
        // Rangers
        for (int i = 0; i < rangers.size(); i++) {
            Ranger ranger = rangers.get(i);
            Rectangle rRect = ranger.getBounds();
            Rectangle rRect_s = ranger.getBound();

            if (yoigStartArea.contains(rRect) || yoigStartArea.intersects(rRect) || rRect.intersects(yoigStartArea) || rRect.contains(yoigStartArea)) {
                if (ranger.isChangeDirection() && ranger.isInDirectionX()) {
                    ranger.setX(ranger.getX() - 1);
                    ranger.setChangeDirection(false);
                } else if (!ranger.isChangeDirection() && ranger.isInDirectionX()) {
                    ranger.setX(ranger.getX() + 1);
                    ranger.setChangeDirection(true);
                } else if (ranger.isChangeDirection() && !ranger.isInDirectionX()) {
                    ranger.setY(ranger.getY() - 1);
                    ranger.setChangeDirection(false);
                } else if (!ranger.isChangeDirection() && !ranger.isInDirectionX()) {
                    ranger.setY(ranger.getY() + 1);
                    ranger.setChangeDirection(true);
                }
            }
            if (yogiRect.intersects(rRect_s) && !yoigStartArea.contains(yogiRect)) {

                yogiNumOfLifes--;
                yogi.x = GameStaticVariables.YOGI_START_X_COORD;
                yogi.y = GameStaticVariables.YOGI_START_Y_COORD;
                if (yogiNumOfLifes == 0) {
                    isAlive = false;
                    String pName = JOptionPane.showInputDialog(this, "Enter your name: ", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
                    if (pName != null) {
                        new yogi_bear.Database(0).putHighScore(pName, score);
                    }
                    int choice = JOptionPane.showConfirmDialog(this, "Restart?", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
                    if (choice == JOptionPane.OK_OPTION) {
                        restart();
                    } else {
                        System.exit(0);
                    }
                }

            }

            for (int idx = 0; idx < obstacles.size(); idx++) {
                Obstacle ob = obstacles.get(idx);
                Rectangle obRect = ob.getBounds();
                if (rRect.intersects(obRect)) {

                    if (ranger.isInDirectionX() && ranger.isChangeDirection()) {
                        ranger.setX(ranger.getX() - 1);
                        ranger.setChangeDirection(false);
                    } else if (!ranger.isChangeDirection() && ranger.isInDirectionX()) {
                        ranger.setX(ranger.getX() + 1);
                        ranger.setChangeDirection(true);
                    } else if (!ranger.isInDirectionX() && ranger.isChangeDirection()) {
                        ranger.setY(ranger.getY() - 1);
                        ranger.setChangeDirection(false);
                    } else if (!ranger.isChangeDirection() && !ranger.isInDirectionX()) {
                        ranger.setY(ranger.getY() + 1);
                        ranger.setChangeDirection(true);
                    }

                }

            }

        }

        for (int idx = 0; idx < obstacles.size(); ++idx) {
            Obstacle ob = obstacles.get(idx);
            Rectangle obRect = ob.getBounds();
            if (yogiRect.intersects(obRect)) {
                if (yogi.getDy() > 0) {
                    yogi.setY(yogi.getY() - 2);
                }
                if (yogi.getDy() < 0) {
                    yogi.setY(yogi.getY() + 2);
                }
                if (yogi.getDx() > 0) {
                    yogi.setX(yogi.getX() - 2);
                }
                if (yogi.getDx() < 0) {
                    yogi.setX(yogi.getX() + 2);
                }
            }

        }

    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent event) {
            yogi.keyReleased(event);
        }

        @Override
        public void keyPressed(KeyEvent event) {
            yogi.keyPressed(event);
        }
    }
}
