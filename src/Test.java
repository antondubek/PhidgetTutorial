import com.phidget22.*;
import processing.core.PApplet;
import processing.core.PFont;

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

    RCServo servo;

    Game game = new Game(this,1800, 600, 51,51,51);

    Ministick myBall = new Ministick(this, game,0,0);
    Rotation myArc = new Rotation(this, game);
    Click myClick = new Click(this, game);
    Sound mySound = new Sound(this, game);

    int stopWatchStart;
    int stopWatchFinish = 0;

    private PFont myFont;


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

            chRota.setDeviceSerialNumber(phidgetNo);
            chLin1.setDeviceSerialNumber(phidgetNo);
            chLin2.setDeviceSerialNumber(phidgetNo);
            redLED.setDeviceSerialNumber(phidgetNo);
            sound.setDeviceSerialNumber(phidgetNo);
            servo.setDeviceSerialNumber(servoNo);
            click.setDeviceSerialNumber(phidgetNo);

            chRota.setChannel(0);
            chLin1.setChannel(1);
            chLin2.setChannel(2);
            sound.setChannel(3);
            redLED.setChannel(0);
            servo.setChannel(0);
            click.setChannel(0);

            //chRota.setDataInterval(1);

            chRota.open();
            chLin1.open();
            chLin2.open();
            redLED.open();
            sound.open();
            servo.open(5000);
            click.open();

            chRota.addVoltageRatioChangeListener(myArc);
            chLin1.addVoltageRatioChangeListener(myBall);
            chLin2.addVoltageRatioChangeListener(myBall);
            click.addStateChangeListener(myClick);
            sound.addVoltageChangeListener(mySound);

            redLED.setState(true);
            servo.setTargetPosition(0);
            //System.out.println(servo.getEngaged());
            servo.setEngaged(true);

            servo.setTargetPosition(45);
            servo.setTargetPosition(90);

        } catch (PhidgetException e) {
            System.out.println(e);
        }

        System.out.println("GO");
        stopWatchStart = millis();

    }

    public void draw() {
        background(game.getBackgroundR(), game.getBackgroundG(), game.getBackgroundB());

        game.draw();
        myBall.draw();
        myArc.draw();
        myClick.draw();
        mySound.draw();

        myFont = createFont("Lobster_1.3.otf", 40);
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

        if(myBall.isSolved() && myArc.isSolved() && mySound.isSolved() && myClick.isSolved() && stopWatchFinish ==0){
            stopWatchFinish = millis();
            String displayText = ("COMPLETED in " + ((stopWatchFinish - stopWatchStart)/1000.0) + " seconds.");
            text(displayText, width/2, (height/10) * 9);
            System.out.println("Solved in "+ (stopWatchFinish - stopWatchStart)/1000.0);
            noLoop();
        } else if (myBall.isSolved() && myArc.isSolved() && myClick.isSolved()){
            text("Clap IT", (width/5) * 4, (height/10) * 8);
        } else if (myBall.isSolved() && myArc.isSolved()){
            text("Click IT", (width/5) * 3, (height/10) * 8);
        } else if (myBall.isSolved()){
            text("Twist IT", (width/5) * 2, (height/10) * 8);
        } else {
            text("Flick IT", width/5, (height/10) * 8);
        }

    }

}




