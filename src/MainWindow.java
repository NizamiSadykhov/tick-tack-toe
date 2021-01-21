import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private static final String WINDOW_TITLE = "Игра: \"Крестики - Нолики\"";
    private static final String NEW_GAME_BUTTON_TITLE = "Новая игра";
    private static final String EXIT_BUTTON_TITLE = "Выход";

    private GameMap gameMap;

    MainWindow() {
        initFrameWindow();
        initBottomPanel();
        initGameMap();
    }

    void startGame(int gameMode, int fieldSize, int winLength) {
        gameMap.startNewGame(gameMode, fieldSize, winLength);
    }

    private void initFrameWindow() {
        setTitle(WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int positionX = (screenSize.width - getWidth()) / 2;
        int positionY = (screenSize.height - getHeight()) / 2;
        setLocation(positionX, positionY);
        setVisible(true);
    }

    private void initGameMap() {
        gameMap = new GameMap();
        add(gameMap);
    }

    private void initBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2));

        JButton btnNewGame = new JButton(NEW_GAME_BUTTON_TITLE);
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Settings(MainWindow.this);
            }
        });

        JButton btnExitGame = new JButton(EXIT_BUTTON_TITLE);
        btnExitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        bottomPanel.add(btnNewGame);
        bottomPanel.add(btnExitGame);

        add(bottomPanel, BorderLayout.SOUTH);
    }
}
