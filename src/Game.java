import com.phidget22.DigitalOutput;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Game {

    private int backgroundR;
    private int backgroundG;
    private int backgroundB;
    private int height;
    private int width;

    // LED Initialisation
    private DigitalOutput redLED;
    private DigitalOutput greenLED;
    private DigitalOutput soundLED;
    private DigitalOutput rotationLED;
    private DigitalOutput joystickLED;

    //LED Helper
    private boolean on = false;

    // State Initialisation
    private int gameState = 1;
    private ArrayList<Integer> myArrayList = new ArrayList<Integer>();

    // Timer Initialisation
    private boolean timeOn = false;
    private long time;
    private long completedIn;


    public Game(int width, int height, int backgroundR, int backgroundG, int backgroundB) {
        this.backgroundR = backgroundR;
        this.backgroundG = backgroundG;
        this.backgroundB = backgroundB;
        this.height = height;
        this.width = width;
        initialiseLEDs();

        // Add to the array list the events you want to happen
        myArrayList.add(2);
        myArrayList.add(3);
        myArrayList.add(4);
        myArrayList.add(5);

        /* DO twice if want
        myArrayList.add(2);
        myArrayList.add(3);
        myArrayList.add(4);
        myArrayList.add(5);
        */
    }

    public void resetGame(){
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

        timeOn = false;
        time = 0;
        completedIn = 0;


    }

    public int getBackgroundR() {
        return backgroundR;
    }

    public int getBackgroundG() {
        return backgroundG;
    }

    public int getBackgroundB() {
        return backgroundB;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getGameState(){
        return this.gameState;
    }

    public void setGameState(int value){
        gameState = value;
    }

    public void randomiseState(){
        if(myArrayList.size() == 0){
            gameState = 6;
        } else {
            int randomNumber = ThreadLocalRandom.current().nextInt(0, myArrayList.size());
            gameState = myArrayList.get(randomNumber);
            myArrayList.remove(randomNumber);
        }
    }

    public void timerStart(){
        timeOn = true;
        this.time = System.currentTimeMillis();
    }

    public void timerStop(){
        timeOn = false;
    }

    public String getTime(){
        if(timeOn) {
            completedIn = System.currentTimeMillis() - time;
        }
        return (new SimpleDateFormat("ss.SSS")).format(completedIn);
    }

    public int getTimeInt(){
        return (int)completedIn;
    }

    public boolean getTimeOn(){
        return timeOn;
    }


    public void initialiseLEDs(){
        int phidgetNo = 274077;

        try {
            redLED = new DigitalOutput();
            greenLED = new DigitalOutput();
            soundLED = new DigitalOutput();
            rotationLED = new DigitalOutput();
            joystickLED = new DigitalOutput();

            redLED.setDeviceSerialNumber(phidgetNo);
            greenLED.setDeviceSerialNumber(phidgetNo);
            soundLED.setDeviceSerialNumber(phidgetNo);
            rotationLED.setDeviceSerialNumber(phidgetNo);
            joystickLED.setDeviceSerialNumber(phidgetNo);

            redLED.setChannel(4);
            greenLED.setChannel(3);
            soundLED.setChannel(5);
            rotationLED.setChannel(6);
            joystickLED.setChannel(7);

            redLED.open();
            greenLED.open();
            soundLED.open();
            rotationLED.open();
            joystickLED.open();
        } catch (Exception e){
            System.out.println(e);
        }

    }

    public void LEDUpdate(){

        if(gameState == 1){
            try{
                soundLED.setState(true);
                rotationLED.setState(true);
                joystickLED.setState(true);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if(gameState == 2 || gameState == 4){
            try{
                soundLED.setState(false);
                rotationLED.setState(false);
                joystickLED.setState(true);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if(gameState == 3){
            try{
                soundLED.setState(false);
                rotationLED.setState(true);
                joystickLED.setState(false);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if(gameState == 5){
            try{
                soundLED.setState(true);
                rotationLED.setState(false);
                joystickLED.setState(false);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if(gameState == 6){
            if(on){
                try{
                    soundLED.setState(false);
                    rotationLED.setState(false);
                    joystickLED.setState(false);
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (Exception e) {
                    System.out.println(e);
                }
                this.on = false;
            } else{
                try{
                    soundLED.setState(true);
                    rotationLED.setState(true);
                    joystickLED.setState(true);
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (Exception e) {
                    System.out.println(e);
                }
                this.on = true;
            }

        } else if(gameState == 7){

        }
    }

    public void setRed(boolean red){
        if (red){
            try {
                redLED.setState(true);
                greenLED.setState(false);
            } catch(Exception e) {
                System.out.println(e);
            }
        } else {
            try {
                redLED.setState(false);
                greenLED.setState(true);
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }
}
