import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;

public class easyLevels8 implements ActionListener {
    private JTextField answerField1, answerField2, answerField3, answerField4;
    private int currentLevel = 1;
    private JLabel timerLabel;
    private Timer timer;
    private int secondsLeft = 30;

    private JLabel hintImageLabel;
    private int hintClickCount = 0;
    private String[] hintMessages = {
        "TAWAG DITO HAYOP",
        "MADALI LANG TO TAWAG NGA HAYOP"
    };
    private int hintMessageIndex = 0;

    public easyLevels8() {
        openGameWindow();
    }

    private void openGameWindow() {
        JFrame gameFrame = new JFrame("Picture Who");
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setSize(1000, 600);
       
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(94, 69, 128));
        gameFrame.getContentPane().add(mainPanel); // main

        JPanel imagePanel = new JPanel(new CustomGridLayout(2, 2, 20, 20));
        imagePanel.setBackground(new Color(94, 69, 128));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));
        mainPanel.add(imagePanel, BorderLayout.CENTER);

        JPanel answerPanel = new JPanel(new GridLayout(1, 1,20,20));
        answerPanel.setBackground(new Color(94, 69, 128));
        answerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        mainPanel.add(answerPanel, BorderLayout.SOUTH);

        // Load different images into ImageIcons
        ImageIcon imageIcon1 = new ImageIcon("img/Chicken-Roll.jpg");
        ImageIcon imageIcon2 = new ImageIcon("img/diceroll.jpg");
        ImageIcon imageIcon3 = new ImageIcon("img/rollinghand.jpg");
        ImageIcon imageIcon4 = new ImageIcon("img/tony-stark-eye-roll.jpg");

        // Scale images to fit within the cells
        Image image1 = imageIcon1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        Image image2 = imageIcon2.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        Image image3 = imageIcon3.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        Image image4 = imageIcon4.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

        ImageIcon scaledImageIcon1 = new ImageIcon(image1);
        ImageIcon scaledImageIcon2 = new ImageIcon(image2);
        ImageIcon scaledImageIcon3 = new ImageIcon(image3);
        ImageIcon scaledImageIcon4 = new ImageIcon(image4);

        // Create JLabels and set the scaled icons
        JLabel imageLabel1 = new JLabel(scaledImageIcon1);
        JLabel imageLabel2 = new JLabel(scaledImageIcon2);
        JLabel imageLabel3 = new JLabel(scaledImageIcon3);
        JLabel imageLabel4 = new JLabel(scaledImageIcon4);

        imagePanel.add(imageLabel1);
        imagePanel.add(imageLabel2);
        imagePanel.add(imageLabel3);
        imagePanel.add(imageLabel4);

        int borderColorRed = 64;
        int borderColorGreen = 64;
        int borderColorBlue = 64;

        imageLabel1.setBorder(BorderFactory.createLineBorder(new Color(borderColorRed, borderColorGreen, borderColorBlue), 4));
        imageLabel2.setBorder(BorderFactory.createLineBorder(new Color(borderColorRed, borderColorGreen, borderColorBlue), 4));
        imageLabel3.setBorder(BorderFactory.createLineBorder(new Color(borderColorRed, borderColorGreen, borderColorBlue), 4));
        imageLabel4.setBorder(BorderFactory.createLineBorder(new Color(borderColorRed, borderColorGreen, borderColorBlue), 4));

        answerField1 = new JTextField();
        answerField2 = new JTextField();
        answerField3 = new JTextField();
        answerField4 = new JTextField();
        

        answerField1 = createSingleLetterTextField(gameFrame, answerField2);
        answerField2 = createSingleLetterTextField(gameFrame, answerField3);
        answerField3 = createSingleLetterTextField(gameFrame, answerField4);
        answerField4 = createSingleLetterTextField(gameFrame, null);


        answerPanel.add(answerField1);
        answerPanel.add(answerField2);
        answerPanel.add(answerField3);
        answerPanel.add(answerField4);


        timerLabel = new JLabel("Time Left: " + secondsLeft);
        timerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Color.WHITE);
        mainPanel.add(timerLabel, BorderLayout.NORTH);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsLeft--;
                if (secondsLeft >= 0) {
                    timerLabel.setText("Time Left: " + secondsLeft);
                } else {
                    timer.stop();
                    int choice = JOptionPane.showConfirmDialog(gameFrame, "Time's up! Do you want to restart the level?", "Restart Level", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        restartLevel();
                    } else {
                        gameFrame.dispose();
                        App game = new App();
                        game.setVisible(true);
                    }
                }
            }
        });
        timer.start();

        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

        addHintPanel(gameFrame);
    }
    private JTextField createSingleLetterTextField(JFrame gameFrame, JTextField nextField) {
        JTextField textField = new JTextField(1);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Arial", Font.BOLD, 30));
        textField.setForeground(new Color(94, 69, 128));
        textField.setBackground(new Color(211, 211, 211)); 
        
        
    Border lineBorder = BorderFactory.createLineBorder(new Color(94, 69, 128), 5, true);
    Border shadowBorder = BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 10);
    Border compoundBorder = new CompoundBorder(lineBorder, shadowBorder);
    textField.setBorder(compoundBorder); 

    Border border = BorderFactory.createLineBorder(new Color(0,0,0), 2, true);
    textField.setBorder(border); 

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char inputChar = e.getKeyChar();
                if (Character.isLetter(inputChar) && textField.getText().length() == 0) {
                    if (nextField != null) {
                        nextField.requestFocusInWindow();
                    }
                }
            }
        });

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswers(gameFrame);
            }
        });

        return textField;
    }
    private void checkAnswers(JFrame gameFrame) {
        String enteredAnswer1 = answerField1.getText().trim().toLowerCase();
        String enteredAnswer2 = answerField2.getText().trim().toLowerCase();
        String enteredAnswer3 = answerField3.getText().trim().toLowerCase();
        String enteredAnswer4 = answerField4.getText().trim().toLowerCase();

        String correctAnswer1 = "r";
        String correctAnswer2 = "o";
        String correctAnswer3 = "l";
        String correctAnswer4 = "l";

       
        if (enteredAnswer1.equals(correctAnswer1) &&
                enteredAnswer2.equals(correctAnswer2) &&
                enteredAnswer3.equals(correctAnswer3) &&
                enteredAnswer4.equals(correctAnswer4)) {
            JOptionPane.showMessageDialog(gameFrame, "Brilliant!");
            gameFrame.dispose();
            openNextLevel();
        } else {
            JOptionPane.showMessageDialog(gameFrame, "Incorrect!");
        }
        answerField1.setText("");
            answerField2.setText("");
            answerField3.setText("");
            answerField4.setText("");
            answerField1.requestFocusInWindow();
    }

    private void openNextLevel() {
        currentLevel++;
         new easyLevels9(); 
    }

    private void restartLevel() {
        new easyLevels8();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openGameWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new easyLevels8(); 
            }
        });
    }
    private void addHintPanel(JFrame gameFrame) {
        ImageIcon hintIcon = new ImageIcon("img/thinking.png");
        Image hintImage = hintIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon scaledHintIcon = new ImageIcon(hintImage);
        hintImageLabel = new JLabel(scaledHintIcon);
        hintImageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hintImageLabel.setToolTipText("Click for hint");
        hintImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (hintClickCount < hintMessages.length) {
                    timer.stop();
                    int choice = JOptionPane.showConfirmDialog(gameFrame, "Do you want to use a hint?", "Hint System", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        String hintMessage;
                        if (hintClickCount == 0) {
                            hintMessage = "Need a hint? Use your wisdom wisely! You have 2 hint trials remaining. Each hint deducts 5 seconds from your remaining time. Choose and think wisely to uncover the hidden word!";
                        } else {
                            hintMessage = "Need a hint? Use your wisdom wisely! You have 1 hint trial remaining. Each hint deducts 5 seconds from your remaining time. Choose and think wisely to uncover the hidden word!";
                        }
                        JOptionPane.showMessageDialog(gameFrame, hintMessage);
                        JOptionPane.showMessageDialog(gameFrame, hintMessages[hintMessageIndex]);
                        hintMessageIndex = (hintMessageIndex + 1) % hintMessages.length;
                        hintClickCount++;
                        if (hintClickCount == hintMessages.length) {
                            hintImageLabel.setEnabled(false);
                            hintImageLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                        if (secondsLeft >= 5) {
                            secondsLeft -= 5;
                            timerLabel.setText("Time Left: " + secondsLeft);
                        } else {
                            secondsLeft = 0;
                            timerLabel.setText("Time Left: " + secondsLeft);
                        }
                    }
                    timer.start();
                }
            }
        });
    
        JPanel hintPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        hintPanel.setBackground(new Color(94, 69, 128));
        hintPanel.add(hintImageLabel);
        gameFrame.getContentPane().add(hintPanel, BorderLayout.SOUTH);}
        
class CustomGridLayout extends GridLayout {
    private int hgap;
    private int vgap;

    public CustomGridLayout(int rows, int cols, int hgap, int vgap) {
        super(rows, cols);
        this.hgap = hgap;
        this.vgap = vgap;
        setHgap(hgap); 
        setVgap(vgap); 
    }

    @Override
    public int getHgap() {
        return hgap;
    }

    @Override
    public int getVgap() {
        return vgap;
    }
}

}