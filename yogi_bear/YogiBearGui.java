package yogi_bear;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.awt.*;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.table.TableColumnModel;

public class YogiBearGui {
    private MenuGui panel ;
    private JMenu inGamemenu;
    private JMenuBar inGamemenuMenuBar;
    private JMenu starGameMenu;
    private JMenuBar starGameMenuBar;
    private JFrame highscoresFrame;
    public JFrame gameFrame;
    private JFrame menuFrame;
    private Database scoresDb;
    private JButton newGameButton;
    private JButton scoresButton;

    public YogiBearGui() throws SQLException,HeadlessException,SQLException{
        panel = new MenuGui(new GridBagLayout());
        scoresDb = new Database(0);
        newGameButton = new JButton("New game");
        scoresButton = new JButton("Scores");
        menuFrame = new JFrame("Start Game Menu");
        updateHighscoresFrame();
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 0, 35, 100);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panel.add(newGameButton, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        panel.add(scoresButton, gridBagConstraints);
        menuFrame.add(panel);
        starGameMenuBar = new JMenuBar();
        menuFrame.setJMenuBar(starGameMenuBar);
        starGameMenu = new JMenu("Menu");
        JMenuItem restartBtn = new JMenuItem("Restart");
        starGameMenu.add(restartBtn);
        
        JMenuItem exit = new JMenuItem("Exit");
        
        starGameMenu.add(exit);
        starGameMenuBar.add(starGameMenu);

        menuFrame.setSize(700, 450);
        menuFrame.setResizable(false);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);
        addEventListeners( exit , restartBtn, newGameButton, scoresButton );
        
    }

    private void addEventListeners(JMenuItem exit , JMenuItem restart,JButton newGameButton,JButton scoresButton){
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                menuFrame.setVisible(true);
            }
        });
    
        newGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
                updateGameFrame();
                gameFrame.setVisible(true);
            }
        });

        scoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (gameFrame != null) {
                    gameFrame.dispose();
                }
                try {
                    updateHighscoresFrame();
                    highscoresFrame.setVisible(true);
                } catch (HeadlessException ex) {
                    Logger.getLogger(YogiBearGui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    
    }
    

    private void updateHighscoresFrame() {
        try {
            highscoresFrame = new JFrame("Highscores");
            JTable scoreTable = new JTable(scoresDb.getDataTable(), scoresDb.getColumnNamesArray());
            scoreTable.setEnabled(false);
            scoreTable.setRowHeight(50);
            JScrollPane sp = new JScrollPane(scoreTable);
            TableColumnModel columnModel = scoreTable.getColumnModel();
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
            columnModel.getColumn(2).setCellRenderer(cellRenderer);
            columnModel.getColumn(1).setCellRenderer(cellRenderer);
            columnModel.getColumn(0).setCellRenderer(cellRenderer);
            highscoresFrame.add(sp);
            highscoresFrame.setSize(GameStaticVariables.FRAME_WIDTH, GameStaticVariables.FRAME_HEIGHT);
            highscoresFrame.setResizable(true);
            highscoresFrame.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(YogiBearGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateGameFrame() {
        gameFrame = new JFrame("Yogi Bear Game");
        gameFrame.add(new GameEngine(this));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inGamemenuMenuBar = new JMenuBar();
        gameFrame.setJMenuBar(inGamemenuMenuBar);
        inGamemenu = new JMenu("Menu");
        JMenuItem restart = new JMenuItem("Restart");
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.dispose();
                updateGameFrame();
                gameFrame.setVisible(true);
            }
        });

        inGamemenu.add(restart);

        JMenuItem main = new JMenuItem("Start Menu");
        main.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.dispose();

            }
        });
        inGamemenu.add(main);
        inGamemenuMenuBar.add(inGamemenu);

        gameFrame.setSize(GameStaticVariables.FRAME_WIDTH , GameStaticVariables.FRAME_HEIGHT + 50);
        gameFrame.setResizable(true);
        gameFrame.setLocationRelativeTo(null);
    }
}
