import com.phidget22.*;
import processing.core.PApplet;

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


    Game game = new Game(1800, 600, 51,51,51);

    Ministick myBall = new Ministick(this, game,0,0);
    Rotation myArc = new Rotation(this, game);
    Click myClick = new Click(this, game);
    Sound mySound = new Sound(this, game);


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



    }

    public void draw() {
        background(game.getBackgroundR(), game.getBackgroundG(), game.getBackgroundB());

        myBall.draw();
        myArc.draw();
        myClick.draw();
        mySound.draw();


    }

}




