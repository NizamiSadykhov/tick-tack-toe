import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JFrame {

    private static final String SETTINGS_TITLE = "Настройки новой игры";
    private static final int WINDOW_WIDTH = 250;
    private static final int WINDOW_HEIGHT = 250;
    private static final String PLAYER_VS_COMPUTER = "Игрок против компьютера";
    private static final String PLAYER_VS_PLAYER = "Игрок против игрока";
    private static final String LABEL_FOR_GAME_MODE = "Выберите режим игры:";
    private static final String LABEL_SIZE_PREFIX = "Размер поля: ";
    private static final String LABEL_WIN_PREFIX = "Выигрышная длина: ";
    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 10;
    private static final String START_BUTTON_TEXT = "Начать игру";

    private MainWindow mainWindow;

    private ButtonGroup radioGroup;
    private JRadioButton playerVsComputer;
    private JRadioButton playerVsPlayer;
    private JLabel fieldSizeLabel;
    private JLabel winLengthLabel;
    private JSlider fieldSizeSlider;
    private JSlider winLengthSlider;
    private JButton btnStartGame;

    Settings(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        initFrameWindow();
        initGameModSwitch();
        initFieldSizePanel();

        btnStartGame = new JButton(START_BUTTON_TEXT);
        btnStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int gameMode;
                if (playerVsComputer.isSelected()){
                    gameMode = GameMap.MODE_PvC;
                } else if (playerVsPlayer.isSelected()) {
                    gameMode = GameMap.MODE_PvP;
                } else {
                    throw new RuntimeException("Unknown mode");
                }

                int fieldSize = fieldSizeSlider.getValue();
                int winLength = winLengthSlider.getValue();

                mainWindow.startGame(gameMode, fieldSize, winLength);
                dispose();
            }
        });
        add(btnStartGame);
    }

    private void initFieldSizePanel() {
        fieldSizeLabel = new JLabel(LABEL_SIZE_PREFIX + MIN_FIELD_SIZE);
        fieldSizeSlider = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        fieldSizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = fieldSizeSlider.getValue();
                fieldSizeLabel.setText(LABEL_SIZE_PREFIX + value);
                winLengthSlider.setMaximum(value);
            }
        });

        winLengthLabel = new JLabel(LABEL_WIN_PREFIX + MIN_FIELD_SIZE);
        winLengthSlider = new JSlider(MIN_FIELD_SIZE, MIN_FIELD_SIZE, MIN_FIELD_SIZE);
        winLengthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                winLengthLabel.setText(LABEL_WIN_PREFIX + winLengthSlider.getValue());
            }
        });

        add(fieldSizeLabel);
        add(fieldSizeSlider);
        add(winLengthLabel);
        add(winLengthSlider);
    }

    private void initGameModSwitch() {
        radioGroup = new ButtonGroup();
        playerVsComputer = new JRadioButton(PLAYER_VS_COMPUTER);
        playerVsPlayer = new JRadioButton(PLAYER_VS_PLAYER);
        radioGroup.add(playerVsComputer);
        radioGroup.add(playerVsPlayer);

        playerVsComputer.setSelected(true);

        add(new JLabel(LABEL_FOR_GAME_MODE));
        add(playerVsComputer);
        add(playerVsPlayer);
    }

    private void initFrameWindow() {
        setTitle(SETTINGS_TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Rectangle rectangle = mainWindow.getBounds();
        int positionX = (int) rectangle.getX() + getWidth() / 2;
        int positionY = (int) rectangle.getY() + getHeight() / 2;
        setLocation(positionX, positionY);

        setResizable(false);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setVisible(true);
    }
}
