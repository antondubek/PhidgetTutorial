import com.phidget22.DigitalOutput;
import processing.core.PApplet;

import java.util.concurrent.TimeUnit;

public class Game extends PApplet {
    private PApplet p;

    private int backgroundR;
    private int backgroundG;
    private int backgroundB;
    private int height;
    private int width;

    // LED Initialisation
    DigitalOutput redLED;
    DigitalOutput greenLED;
    DigitalOutput soundLED;
    DigitalOutput rotationLED;
    DigitalOutput joystickLED;

    //LED Helper
    private boolean on = false;


    public Game(PApplet p, int width, int height, int backgroundR, int backgroundG, int backgroundB) {
        this.p = p;
        this.backgroundR = backgroundR;
        this.backgroundG = backgroundG;
        this.backgroundB = backgroundB;
        this.height = height;
        this.width = width;
        initialiseLEDs();
    }

    public void draw(){

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

    public void LEDControl(int gameState){

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
