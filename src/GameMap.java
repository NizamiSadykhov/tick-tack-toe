import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GameMap extends JPanel {
    public static final int MODE_PvC = 0;
    public static final int MODE_PvP = 1;
    private int gameMode;

    private int fieldSize;
    private int winLength;

    private int[][] field;
    private static final int EMPTY = 0;
    private static final int PLAYER_ONE = 3;
    private static final int PLAYER_TWO = 4;
    private static final Color PLAYER_ONE_COLOR = Color.RED;
    private static final Color PLAYER_TWO_COLOR = Color.BLUE;

    private boolean isGameStarted = false;

    private int cellWidth;
    private int cellHeight;

    private int turnCount;


    void startNewGame(int gameMode, int fieldSize, int winLength) {
        turnCount = 0;
        this.gameMode = gameMode;
        this.fieldSize = fieldSize;
        this.winLength = winLength;
        field = new int[fieldSize][fieldSize];
        repaint();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseReleased(e);
                clickPoint(e);
            }
        });

        isGameStarted = true;
    }

    private void clickPoint(MouseEvent e) {
        int x = e.getX() / cellWidth;
        int y = e.getY() / cellHeight;

        if (!isEmptyPoint(x, y)) {
            return;
        }

        if (gameMode == MODE_PvC) {
            playerVsComputer(x, y);
        } else if (gameMode == MODE_PvP) {
            playerVsPlayer(x, y);
        }

        repaint();
    }

    private boolean checkWin(int player) {
        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                if (checkLine(x, y, 1, 0, player))
                    return true;
                else if (checkLine(x, y, 0, 1, player))
                    return true;
                else if (checkLine(x, y, 1, 1, player))
                    return true;
                else if (checkLine(x, y, 1, 0, player))
                    return true;
            }
        }

        return false;
    }

    private boolean checkLine(int x, int y, int vX, int vY, int player){
        int lengthX = x + (winLength - 1) * vX;
        int lengthY = y + (winLength - 1) * vY;

        if (!isValidTurn(lengthX, lengthY))
            return false;

        for (int i = 0; i < winLength; i++) {
            if (field[y + i * vX][y + i * vY] != player)
                return false;
        }

        return true;
    }

    private void playerVsComputer(int x, int y) {
        field[y][x] = PLAYER_ONE;
        if (isMapFill()) {
            System.out.println("количество ходов закончилось!");
        } else {
            turnComputer();
        }
    }

    private void playerVsPlayer(int x, int y) {
        turnCount++;
        if (turnCount % 2 == 1){
            field[y][x] = PLAYER_ONE;
            if (isMapFill()) System.out.println("количество ходов закончилось!");
            if (checkWin(PLAYER_ONE)) System.out.println("Победил игрок 1");
        } else {
            field[y][x] = PLAYER_TWO;
        }
        if (isMapFill()) System.out.println("количество ходов закончилось!");
        repaint();
    }

    private boolean isMapFill() {
        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                if (field[y][x] == EMPTY) return false;
            }
        }
        return true;
    }


    private void turnComputer() {
        int x, y;
        boolean isValid;

        do {
            x = (int) (fieldSize * Math.random());
            y = (int) (fieldSize * Math.random());
            isValid = isValidTurn(x, y);
        } while (!isValid);

        field[y][x] = PLAYER_TWO;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        if (!isGameStarted) {
            return;
        }
        int wight = getWidth();
        int height = getHeight();

        cellWidth = wight / fieldSize;
        cellHeight = height / fieldSize;

        g.setColor(Color.BLACK);

        for (int i = 1; i < fieldSize; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, height);
        }

        for (int i = 1; i < fieldSize; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, wight, y);
        }

        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                if (isEmptyPoint(x, y)) {
                    continue;
                }

                if (field[y][x] == PLAYER_ONE) {
                    g.setColor(PLAYER_ONE_COLOR);
                    g.fillOval(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                } else if (field[y][x] == PLAYER_TWO) {
                    g.setColor(PLAYER_TWO_COLOR);
                    g.fillOval(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                } else {
                    throw new RuntimeException("Unexpected turn");
                }
            }
        }
    }

    private boolean isEmptyPoint(int x, int y) {
        return field[y][x] == EMPTY;
    }

    private boolean isValidTurn(int x, int y) {
        return x < fieldSize || x >= 0
                || y < fieldSize || y >= 0;
    }
}
