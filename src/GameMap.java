import javax.swing.*;
import java.awt.*;

public class GameMap extends JPanel {
    public static final int MODE_PvC = 0;
    public static final int MODE_PvP = 1;


    void startNewGame(int gameMode, int fieldSize, int winLength) {
        setLayout(new GridLayout(fieldSize, fieldSize));
        for (int i = 0; i < fieldSize * fieldSize; i++) {
            add(new JButton());
        }
        updateUI();
    }
}
