import com.phidget22.*;
import processing.core.PApplet;
import processing.core.PFont;

import java.text.SimpleDateFormat;

public class Test extends PApplet{

    public static void main(String[] args) {
        PApplet.main("Test");

    }

    VoltageRatioInput chRota; // The channel for the Rotation sensor
    VoltageRatioInput chLin1;
    VoltageRatioInput chLin2;
    DigitalOutput redLED;
    DigitalInput click;
    VoltageInput sound;
    VoltageRatioInput light;

    RCServo servo;

    Game game = new Game(this,1800, 600, 51,51,51);

    Ministick myBall = new Ministick(this, game,0,0);
    Rotation myArc = new Rotation(this, game);
    Click myClick = new Click(this, game);
    Sound mySound = new Sound(this, game);
    Light myLight = new Light(this, game);

    boolean timeOn = false;
    long time;
    long completedIn;

    private PFont myFont;
    private String goText = "Cover Sensor to Start";

    int gameState = 1;


    public void settings(){

        size(game.getWidth(),game.getHeight());

        int phidgetNo = 274077;
        int servoNo = 306007;



        // Makes things look nicer with some Anti-Aliasing
        smooth();

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

            //chRota.setDataInterval(1);

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



    }

    public void setup(){
        myFont = createFont("Lobster_1.3.otf", 40);
    }

    public void draw() {
        background(game.getBackgroundR(), game.getBackgroundG(), game.getBackgroundB());

        game.draw();

        fill(255,255,255);
        stroke(255,255,255);
        textSize(50);
        textFont(myFont);
        text("Welcome to PhidgIT", width/2, height/10);
        textAlign(CENTER, TOP);

        textSize(32);
        textAlign(CENTER, CENTER);

        int width = game.getWidth();
        int height = game.getHeight();

        text(goText, width/2, (height/10) * 9);

        if(gameState == 1){
            if(myLight.getSensorReading() < 10 && !timeOn){
                goText = "GO!!!";
                timeOn = true;
                this.time = System.currentTimeMillis();
                gameState = 2;
            }
        } else if(gameState == 2){
            myBall.draw();
            goText = getTime();
            if(myBall.isSolved()){
                gameState = 3;
            }
        } else if(gameState == 3){
            myArc.draw();
            goText = getTime();
            myBall.draw();
            if(myArc.isSolved()){
                gameState = 4;
            }
        } else if(gameState == 4){
            myClick.draw();
            goText = getTime();
            myBall.draw();
            myArc.draw();
            if(myClick.isSolved()){
                gameState = 5;
            }
        } else if(gameState == 5){
            mySound.draw();
            myBall.draw();
            myArc.draw();
            myClick.draw();
            goText = getTime();
            if(mySound.isSolved()){
                gameState = 6;
            }
        } else if(gameState == 6){
            timeOn = false;
            goText = "";

            fill(255,255,255);
            stroke(255,255,255);
            textSize(50);
            String newText = ("COMPLETED in " + getTime() + " seconds.");
            text(newText, width/2, (height/2));
        }



    }

    public String getTime(){
        if(timeOn) {
            completedIn = System.currentTimeMillis() - time;
        }
        return (new SimpleDateFormat("ss:SSS")).format(completedIn);
    }

}




