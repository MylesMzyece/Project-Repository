import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.*;

public class GUI implements KeyListener {
    //int for countdown
    private int countdown = 120;
    //timer label
    private JLabel timerLabel;
    //label for score
    private JLabel scoreLabel;
    //label for instructions
    private JLabel instructionLabel;
    private JLabel instructionLabel2;
    //score count
    private int score = 0;
    //text twist image
    private JLabel textTwistImage;
    //array of buttons at the bottom
    private JButton[] buttonArray;
    //array of spaces/rectangles that the letters enter into
    private JPanel[] rectangleArray;
    //enter button
    private JButton enter;
    //array of spaces above
    private ArrayList<JRectangle[]> spaces;
    //reset button
    private JButton resetButton;
    public ArrayList<String> anagrams;
    private boolean activeTimer = true;

    JFrame frame = new JFrame("Countdown Timer");;

    JPanel panel = new JPanel(null);

    public GUI(ArrayList<String> anagrams, String importantWord) {

        // Initialize the anagrams list and the word for the game
        this.anagrams = anagrams;
        // Set up the frame
        setUpFrame();
        // Set up the key input
        setUpKeyInput();
        // Set up the TextTwist image
        setTextTwistImage();
        // Set up the timer
        setUpTimer();
        //set up the score
        setUpScore();
        // Set up the user input sections (rectangles and buttons)
        setUpUserInput(importantWord);
        // Generate the board with the important word and anagrams
        generateBoard(importantWord, anagrams);
        // Make the frame visible
        frame.setVisible(true);
    }



    // Custom Rectangle Drawing JPanel
    // used chatgpt to ask: how to make a jRectangle in java graphics because I wanted to add text to a square
    private class JRectangle extends JPanel {
        private JLabel label;

        public JRectangle() {
            // center the text in the rectangle
            this.label = new JLabel("", SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 20));
            label.setForeground(Color.BLACK);
            // Use BorderLayout to center the label inside the rectangle
            this.setLayout(new BorderLayout());
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            this.setPreferredSize(new Dimension(30, 30));
            // Add the label to the center of the rectangle
            this.add(label, BorderLayout.CENTER);
        }

        public void setLetter(String letter) {
            // Update the label's text (i.e., letter)
            label.setText(letter);
        }
    }
// sets up the graphics frame
    private void setUpFrame(){
        //Set Up the frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.9);
        int height = (int) (screenSize.height * 0.9);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        // Center the window
        frame.setLocationRelativeTo(null);
        frame.setContentPane(panel);
    }

    //adds Key Listeners
    private void setUpKeyInput(){
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        panel.addKeyListener(this);
    }

    //Set up the image of Text Twist
    private void setTextTwistImage (){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon icon = new ImageIcon("TextTwist/TextTwist.png");
        Image img = icon.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        textTwistImage = new JLabel(new ImageIcon(img));
        textTwistImage.setBounds(0, 0, screenSize.width, screenSize.height);
        textTwistImage.setVisible(true);
        panel.add(textTwistImage);

        //mouse listeners for initial click of text twist image
        textTwistImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textTwistImage.setVisible(false);
                startTimer();
                startScoring();

            }
        });
    }

    //adds the timer text bubble and begins countdown
    private void setUpTimer(){
        timerLabel = new JLabel("", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        timerLabel.setBounds(0, 50, frame.getWidth(), 50);
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setVisible(false);
        panel.add(timerLabel);
    }
    //adds the score label and score to the screen
    private void setUpScore(){
        // Add the timer
        scoreLabel = new JLabel("", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel.setBounds(0, 100, frame.getWidth(), 50);
        scoreLabel.setForeground(Color.BLACK);

        // Initially hide the timer label
        scoreLabel.setVisible(false);
        panel.add(scoreLabel);
    }
//sets up all aspects of user input, including rectangles, buttons, enter, and instructions
    private void setUpUserInput(String importantWord){
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        //create array of rectangles - with editable letter fill ins
        rectangleArray = new JPanel[6]; // Array of 6 rectangles (you can change this size)
        int rectXPos = frameWidth / 4;
        int rectYPos = frameHeight - 200;
        for (int i = 0; i < rectangleArray.length; i++) {
            rectangleArray[i] = new JRectangle();  // Create a rectangle (JPanel with JLabel)
            rectangleArray[i].setBounds(rectXPos, rectYPos, 85, 50); // Set bounds for each rectangle
            rectangleArray[i].setVisible(true);
            panel.add(rectangleArray[i]);
            rectXPos += 100;  // Move next rectangle to the right
        }

        //make enter button
        enter = new JButton("Enter");
        enter.setBounds(frameWidth - 300, rectYPos, 100, 50);
        enter.setEnabled(false);
        panel.add(enter);
        enter.addActionListener(e -> correctWord(processGuess(), importantWord));
        enter.setEnabled(true);
        enter.setVisible(true);


        buttonArray = new JButton[6];
        for(int i = 0; i < 6; i++) {
            buttonArray[i]=new JButton();
        }

        int xPos = frameWidth / 4;
        int yPos = rectYPos + 80;  // Position the buttons slightly below the timer

        for (JButton btn : buttonArray) {
            btn.setBounds(xPos, yPos, 85, 50); // Set the bounds of each button
            btn.addActionListener(new ButtonClickListener());
            panel.add(btn);  // Add each button to the panel
            xPos += 100;  // Increment X position to space buttons horizontally
        }
        //add instructions
        instructionLabel = new JLabel("", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        instructionLabel.setBounds(0, yPos - 120, frameWidth, 50);
        instructionLabel.setForeground(Color.BLACK);


        panel.add(instructionLabel);
        instructionLabel.setVisible(true);
        instructionLabel.setText("Press Space to Shuffle");

        instructionLabel2 = new JLabel("", SwingConstants.CENTER);
        instructionLabel2.setFont(new Font("Arial", Font.BOLD, 20));
        instructionLabel2.setBounds(0, yPos - 160, frameWidth, 50);
        instructionLabel2.setForeground(Color.BLACK);


        panel.add(instructionLabel2);
        instructionLabel2.setVisible(true);
        instructionLabel2.setText("Press delete to remove");
    }

    // Button Click Listener (for letter selection)
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!activeTimer) {
                disableButtons();
            }
            JButton source = (JButton) e.getSource();
            int index = -1;

            boolean found = false;
            // Find the first empty rectangle to place the letter
            for (int i = 0; i < rectangleArray.length&&!found; i++) {
                if (((JRectangle) rectangleArray[i]).label.getText().equals("")) {
                    index = i;
                    found = true;
                }
            }

            if (index != -1) {
                // Set the letter in the corresponding rectangle
                ((JRectangle) rectangleArray[index]).setLetter(source.getText());
                rectangleArray[index].setVisible(true);
            }
            panel.requestFocusInWindow();
        }
    }

    //handles all keyboard input
    @Override
    public void keyTyped(KeyEvent e) {
        if(!activeTimer)return; //checks if timer is still running and returns if game has ended

        int index = -1;
        char keyChar = e.getKeyChar();
        int charNum = (int)e.getKeyChar(); //ASCII Value

        //creates string of possible letters that can be used
        StringBuilder sb = new StringBuilder();
        for (JButton btn : buttonArray) {
            sb.append((btn).getText());
            // Appends letter from each rectangle
        }
        String formedWord = sb.toString();

        //enter is pressed
        if(charNum == 10) {
            correctWord(processGuess(), formedWord);
            return;
        }

        //space is pressed, shuffles words
        if(charNum==32){
            addWord(Game.shuffle(formedWord));
            return;
        }

        // invalid character is used, plays buzzer noise
        if(!formedWord.toLowerCase().contains(String.valueOf(keyChar))&&charNum != 8&&charNum != 10&&charNum != 32){
            playSound("TextTwist/buzzer.wav");
            return;
        }

        boolean found = false;
        // Find the first empty rectangle to place the letter
        for (int i = 0; i < rectangleArray.length&&!found; i++) {
            if (((JRectangle) rectangleArray[i]).label.getText().equals("")) {
                index = i;
                found = true;
            }
        }

        //removes last character if backspace is pressed
        if (charNum == 8) {
            int lastFilledIndex = -1;
            for (int i = 0; i < rectangleArray.length; i++) {
                if (!((JRectangle) rectangleArray[i]).label.getText().equals("")) {
                    lastFilledIndex = i; //index of last filled character
                }
            }

            //empties the last rectangle
            if (lastFilledIndex != -1) {
                ((JRectangle) rectangleArray[lastFilledIndex]).setLetter("");
            }
            return;
        }

        // Sets the letter in the corresponding rectangle
        if (index != -1) {
            ((JRectangle) rectangleArray[index]).setLetter(String.valueOf(keyChar));
            rectangleArray[index].setVisible(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private String processGuess() {
        StringBuilder sb = new StringBuilder();
        // Check if the word is complete
        for (JPanel rectangle : rectangleArray) {
            // Append letter from each rectangle
            sb.append(((JRectangle) rectangle).label.getText());
            ((JRectangle) rectangle).label.setText("");

        }
        String formedWord = sb.toString();

        return formedWord;

    }
//once enter is pressed, this method calls game to check if the word is in the anagrams list, then adds the word to the top if it is
    public void correctWord(String formedWord, String importantWord) {
        int index = anagrams.indexOf(formedWord);
        int winScore=0;
        if (Game.checkWord(formedWord,importantWord)>0) {
            for (int i = 0; i < formedWord.length(); i++) {
                spaces.get(index)[i].setLetter(String.valueOf(formedWord.charAt(i)));
            }
            //this also checks if all the words have been found and the game has been won
            score+=formedWord.length();
            if(score==winScore){
                scoreLabel.setText("YOU WIN");
            }else {
                scoreLabel.setText("Score: " + score);
            }
        }
    }

    //creates the word and the above spaces

    public void generateBoard(String word, ArrayList<String> anagrams) {
        addWord(Game.shuffle(word));
        addSpaces(anagrams);
    }

    //starts the timer for the game once the text twist screen is clicked
    private void startTimer() {
        timerLabel.setVisible(true); // Show the timer label
        timerLabel.setText("Time Left: " + countdown + " seconds");

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (countdown > 0) {
                    countdown--;
                    timerLabel.setText("Time Left: " + countdown + " seconds");
                } else {
                    timerLabel.setText("Time's up!");
                    timerLabel.setForeground(Color.RED);
                    timer.cancel();
                    activeTimer=false;
                    //GUI class implements key listeners so it can't be removed with
                    // standard method so this boolean is used instead to stop keyboard input after time is up
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }
    //starts the scoring once the text twist screen is clicked
    private void startScoring(){
        scoreLabel.setVisible(true);
        scoreLabel.setText("Score: " + score);
    }

//plays sound to make audio work with an imported file path for the audio clip
    private void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //adds the spaces above the user input screen
    private void addSpaces(ArrayList<String> anagrams) {
        // Calculate how many words we need to display
        int wordsCount = anagrams.size();

        // Rectangle dimensions for each letter space
        int rectWidth = 30;
        int rectHeight = 30;

        // Start below the timer/score
        int yPos = 160;
        int xPos = 15;

        spaces = new ArrayList<>();
        int frameWidth = frame.getWidth();

        // Loop through all the anagram words
        for (int i = 0; i < wordsCount; i++) {
            String word = anagrams.get(i);
            int wordWidth = (rectWidth + 2) * word.length() + 6;

            // If the word won't fit in the remaining space, move to the next line
            if (xPos + wordWidth > frameWidth - 15) {
                xPos = 15;
                yPos += rectHeight + 10;
            }
            JRectangle[] wordSpaces = new JRectangle[word.length()];
            for (int j = 0; j < word.length(); j++) {
                wordSpaces[j] = new JRectangle();
                wordSpaces[j].setBounds(xPos, yPos, rectWidth, rectHeight);
                wordSpaces[j].setVisible(true);
                panel.add(wordSpaces[j]);
                xPos += rectWidth + 2;
            }
            spaces.add(wordSpaces);
            xPos += 6;
        }
    }


//adds the word to the screen by setting the button labels to each individual letter
    private void addWord(String importantWord) {
        for(int i=0; i<importantWord.length();i++) {
            buttonArray[i].setText(String.valueOf(importantWord.charAt(i)));
            buttonArray[i].setVisible(true);
            buttonArray[i].setEnabled(true);
        }
    }

//disables buttons after time runs out
    private void disableButtons(){
        for(int i = 0; i < buttonArray.length; i++) {
            buttonArray[i].setEnabled(false);
        }

    }

//main method that creates and runs the game, loads dictionary, and makes instance of GUI
    public static void main(String[] args) throws FileNotFoundException {
        String importantWord;
        Game game = new Game();
        File dictionaryFile = new File("TextTwist/FinalDictionary.txt");
        game.loadDictionary(dictionaryFile);
        File dictionarySixFile = new File("TextTwist/SixLetterDictionary.txt");
        game.loadSixLetterDictionary(dictionarySixFile);


        importantWord = game.createWord();
        GUI board=new GUI(Game.getAnagrams(importantWord), importantWord);

    }
}
