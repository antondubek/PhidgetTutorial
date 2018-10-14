import com.phidget22.*;
import processing.core.PApplet;
import processing.core.PFont;
import processing.data.Table;
import processing.data.TableRow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Test extends PApplet{

    public static void main(String[] args) {
        PApplet.main("Test");

    }

    // Initialisation of Inputs and Outputs
    VoltageRatioInput chRota;
    VoltageRatioInput chLin1;
    VoltageRatioInput chLin2;
    DigitalOutput redLED;
    DigitalInput click;
    VoltageInput sound;
    VoltageRatioInput light;
    RCServo servo;

    // Creation of Output objects
    Game game = new Game(this,1260, 720, 51,51,51);
    Ministick myBall = new Ministick(this, game,0,0);
    Rotation myArc = new Rotation(this, game);
    Click myClick = new Click(this, game);
    Sound mySound = new Sound(this, game);
    Light myLight = new Light(this, game);

    // Timer Initialisation
    boolean timeOn = false;
    long time;
    long completedIn;

    // Font initialisation
    private PFont myFont;
    private String goText = "Cover Sensor to Start";

    // State Initialisation
    int gameState = 1;

    ArrayList<Integer> myArrayList = new ArrayList<Integer>();

    public ArrayList<Integer> getMyArrayList = new ArrayList<Integer>();

    // Leaderboard Initialisation
    char[] letters = new char[4]; // 4 letters
    int index=0;                  // which letter is active
    String result="";
    Table table;
    String savegame = "savescore.csv";
    final int N = 5; // entries in high score



    public void settings(){

        size(game.getWidth(),game.getHeight());

        // Makes things look nicer with some Anti-Aliasing
        smooth();
    }

    public void setup(){
        myFont = createFont("Lobster_1.3.otf", 40);

        int phidgetNo = 274077;
        int servoNo = 306007;

        try {
            chRota = new VoltageRatioInput();
            chLin1 = new VoltageRatioInput();
            chLin2 = new VoltageRatioInput();
            redLED = new DigitalOutput();
            servo = new RCServo();
            click = new DigitalInput();
            sound = new VoltageInput();
            light = new VoltageRatioInput();

            chRota.setDeviceSerialNumber(phidgetNo);
            chLin1.setDeviceSerialNumber(phidgetNo);
            chLin2.setDeviceSerialNumber(phidgetNo);
            redLED.setDeviceSerialNumber(phidgetNo);
            sound.setDeviceSerialNumber(phidgetNo);
            servo.setDeviceSerialNumber(servoNo);
            click.setDeviceSerialNumber(phidgetNo);
            light.setDeviceSerialNumber(phidgetNo);

            chRota.setChannel(0);
            chLin1.setChannel(1);
            chLin2.setChannel(2);
            sound.setChannel(3);
            redLED.setChannel(0);
            servo.setChannel(0);
            click.setChannel(0);
            light.setChannel(4);

            chRota.open();
            chLin1.open();
            chLin2.open();
            redLED.open();
            sound.open();
            servo.open(5000);
            click.open();
            light.open();

            chRota.addVoltageRatioChangeListener(myArc);
            chLin1.addVoltageRatioChangeListener(myBall);
            chLin2.addVoltageRatioChangeListener(myBall);
            click.addStateChangeListener(myClick);
            sound.addVoltageChangeListener(mySound);
            light.addVoltageRatioChangeListener(myLight);

            redLED.setState(true);
            servo.setTargetPosition(0);
            //System.out.println(servo.getEngaged());
            servo.setEngaged(true);

            servo.setTargetPosition(45);
            servo.setTargetPosition(90);

        } catch (PhidgetException e) {
            System.out.println(e);
        }

        myArrayList.add(2);
        myArrayList.add(3);
        myArrayList.add(4);
        myArrayList.add(5);
        myArrayList.add(2);
        myArrayList.add(3);
        myArrayList.add(4);
        myArrayList.add(5);

        // Leaderboard setup
        // letters for Input
        for (int i=0; i<letters.length; i++) {
            letters[i]='A';
        }

        //try to load table
        table = null;
        table = loadTable(savegame, "header");
        // success?
        if (table!=null) {
            //success
            for (TableRow row : table.rows()) {
                int id = row.getInt("id");
                      //String scores[id] = row.getString("savedata");
            }
        } else {
            // fail:
            // first run, make table
            table = new Table();
            table.addColumn("id");
            table.addColumn("savedata");
            table.addColumn("name");
            for (int i = 0; i<N; i++) {
                TableRow newRow = table.addRow();
                newRow.setInt("id", table.lastRowIndex());
                newRow.setInt("savedata", 99999);
                newRow.setString("name", "");
            }
        }
    }

    public void draw() {
        background(game.getBackgroundR(), game.getBackgroundG(), game.getBackgroundB());

        game.draw();

        fill(255,255,255);
        stroke(255,255,255);

        textSize(32);
        textAlign(CENTER, CENTER);
        textFont(myFont);

        int width = game.getWidth();
        int height = game.getHeight();

        text(goText, width/2, (height/10) * 9);

        //if(myBall.isSolved() && myArc.isSolved() && mySound.isSolved() && myClick.isSolved()){
        //    gameState = 6;
        //}

        if(gameState == 1){
            printTopText();
            goText = "Cover Sensor to Start";
            if(myLight.getSensorReading() < 10 && !timeOn){
                goText = "GO!!!";
                timeOn = true;
                this.time = System.currentTimeMillis();
                randomState();
            }
        } else if(gameState == 2){

            printTopText();
            myBall.draw();
            goText = getTime();
            if(myBall.isSolved()){
                myBall.newGame();
                randomState();
            }
        } else if(gameState == 3){

            printTopText();
            myArc.draw();
            goText = getTime();
            if(myArc.isSolved()){
                myArc.newGame();
                randomState();
            }
        } else if(gameState == 4){

            printTopText();
            myClick.draw();
            goText = getTime();
            if(myClick.isSolved()){
                myClick.newGame();
                randomState();
            }
        } else if(gameState == 5){
            printTopText();
            mySound.draw();
            goText = getTime();
            if(mySound.isSolved()){
                mySound.newGame();
                randomState();
            }
        } else if(gameState == 6){
            timeOn = false;
            goText = "";

            fill(255,255,255);
            stroke(255,255,255);
            textSize(50);
            String newText = ("COMPLETED in " + getTime() + " seconds.");
            text(newText, width/2, (height/8));

            printHighscores();

            textAlign(CENTER, CENTER);
            textSize(32);
            fill(255,255,255);

            if(highEnoughForHighScore(getTimeInt())){
                text("Press space to enter name, N for new game or Q to Quit", width/2, (height/10) * 8);
            } else {
                text("Score not high enough for top 5, press N for new game or Q to Quit", width/2, (height/10) * 8);
            }

        }
        else if(gameState == 8){
            fill(255, 255, 255); // red = default
            text("Please enter your name", width/2, height/4);
            textSize(32);
            textAlign(CENTER, CENTER);
            text("Use cursor left right and up and down, return to finish input", width/2, height/10 * 8);
            textSize(80);

            int i=0;
            for (char c : letters) {
                fill(255, 255, 255);
                if (i==index)
                    fill(255);
                text((char)(letters[i])+"", ((width/2))+i*65, height/2);
                i++;
            }
        } else if (gameState == 9){
            // state after the Input

            result=""+letters[0]+letters[1]+letters[2]+letters[3];
            println(result);

            System.out.println("DEBUG: Floaty Time = " + Float.parseFloat(getTime()));
            addNewScore(getTimeInt(), result);
            saveScores();
        } else if(gameState == 10) {
            fill(255, 255, 255);
            textSize(50);
            text("Highscores", width / 2, (height / 8));

            printHighscores();

            textSize(32);
            textAlign(CENTER, CENTER);
            fill(255, 255, 255);
            text("Press SPACE for new game or Q to quit", width / 2, (height / 10) * 9);
        }

        else if(gameState == 11){
            newGame();
        }



    }

    public void randomState(){
        if(myArrayList.size() == 0){
            gameState = 6;
        } else {
            int randomNumber = ThreadLocalRandom.current().nextInt(0, myArrayList.size());
            gameState = myArrayList.get(randomNumber);
            myArrayList.remove(randomNumber);
        }
    }


    public void printHighscores(){
        fill(0, 191, 255);
        textAlign(LEFT, CENTER);
        for (int i = 0; i < N; i++) {
            TableRow newRow = table.getRow(i);

            String x = Integer.toString(newRow.getInt("savedata"));
            //System.out.println(x.length());
            if(x.length() == 4){
                x = x.charAt(0) + "." + x.substring(1, x.length());
            } else {
                x = x.substring(0,2) + "." + x.substring(3, x.length());
            }

            text((i+1) + ". " + x
                    + " " + newRow.getString("name"), (width / 5) * 2, ((height / 8) * 2) + 80 * i);
        }
    }

    public void keyPressed() {

        // state tells how the program works:
        if (gameState==6) {

            if (key == ' ') { // Press space to add score
                letters = new char[4];
                for (int i=0; i<letters.length; i++) {
                    letters[i]='A';
                }
                // add one element to high scores
                if (highEnoughForHighScore(getTimeInt())) {
                    gameState=8;
                }
            } else if (key == 'n'){
                gameState = 11;
            } else if (key == 'q' || key == 'Q'){
                exit();
            }
        }// state
        // -------------------
        else  if (gameState==8) {
            // state for the Input

            if (keyCode == UP) {
                if (letters[index] > 64) {
                    letters[index]--;
                    if (letters[index] == 64) {
                        letters[index] = 90;
                    }
                }
            } else if (keyCode == DOWN) {
                if (letters[index] < 91) {
                    letters[index]++;
                    if (letters[index] == 91) {
                        letters[index] = 65;
                    }
                }
            } else if (keyCode == LEFT) {
                index--;
                if (index<0)
                    index=0;
            } else if (keyCode == RIGHT) {
                index++;
                if (index>3)
                    index=3;
            } else if (key == RETURN||key==ENTER) {
                gameState=9;
            }
        } else if (gameState==10){
            if(key == ' '){
                gameState = 11;
            }
            if (key == 'q' || key == 'Q'){
                exit();
            }
        }

        //state
    }//func

    public String getTime(){
        if(timeOn) {
            completedIn = System.currentTimeMillis() - time;
        }
        return (new SimpleDateFormat("ss.SSS")).format(completedIn);
    }

    public int getTimeInt(){
        return (int)completedIn;
    }


    void addNewScore(int score, String name) {
        //
        // we add a new row, table is too long now
        TableRow newRow = table.addRow();
        newRow.setInt("id", table.lastRowIndex());
        newRow.setInt("savedata", (score));
        newRow.setString("name", name);

        // we sort
        table.setColumnType("savedata", Table.INT);
        table.trim();  // trim
        table.sort(1); // sort backwards by score

        // test
        println ("---");
        for (int i = 0; i<table.getRowCount(); i++) {
            newRow = table.getRow(i);
            println (newRow.getInt("savedata"), newRow.getString("name"));
        }
        println ("---");

        // we delete items
        if (table.getRowCount()>5)
            for (int i=table.getRowCount()-1; i>=5; i--) {
                TableRow row=table.getRow(i);
                table.removeRow(i);
            }

        // test
        println ("---");
        for (int i = 0; i<table.getRowCount(); i++) {
            newRow = table.getRow(i);
            println (newRow.getInt("savedata"), newRow.getString("name"));
        }
        println ("---");

        gameState = 10;
    }

    void saveScores() {
        saveTable(table, "data/savescore.csv");
    }

    boolean highEnoughForHighScore(int score) {

        // test whether new score is high enough to get into the highscore

        for (TableRow newRow : table.rows()) {
            //If the score is lower than the top 5

            if ((score < newRow.getInt("savedata"))) {
                return true; // high enough
            }
        }
        return false; // NOT high enough
    }

    public void printTopText(){
        textSize(50);
        text("Welcome to PhidgIT", width/2, height/10);
        textAlign(CENTER, TOP);
    }

    public void newGame(){
        timeOn = false;
        time = 0;
        completedIn = 0;

        myArrayList.add(2);
        myArrayList.add(3);
        myArrayList.add(4);
        myArrayList.add(5);
        myArrayList.add(2);
        myArrayList.add(3);
        myArrayList.add(4);
        myArrayList.add(5);

        // State Initialisation
        gameState = 1;

        // Leaderboard Initialisation
        letters = new char[4]; // 4 letters
        index=0;                  // which letter is active
        result="";

        final int N = 5; // entries in high score
    }
}




