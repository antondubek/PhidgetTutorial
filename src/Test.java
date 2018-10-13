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
    RCServo servo;


    Ministick myBall = new Ministick(0,0);


    public void settings(){
        size(600,600);

        try {
            chRota = new VoltageRatioInput();
            chLin1 = new VoltageRatioInput();
            chLin2 = new VoltageRatioInput();
            redLED = new DigitalOutput();
            servo = new RCServo();
            click = new DigitalInput();

            chRota.setDeviceSerialNumber(274077);
            chLin1.setDeviceSerialNumber(274077);
            chLin2.setDeviceSerialNumber(274077);
            redLED.setDeviceSerialNumber(274077);
            servo.setDeviceSerialNumber(306007);
            click.setDeviceSerialNumber(274077);

            chRota.setChannel(0);
            chLin1.setChannel(1);
            chLin2.setChannel(2);
            redLED.setChannel(0);
            servo.setChannel(0);
            click.setChannel(0);

            //chRota.setDataInterval(1);

            chRota.open();
            chLin1.open();
            chLin2.open();
            redLED.open();
            servo.open(5000);
            click.open();

            chRota.addVoltageRatioChangeListener(myBall);
            chLin1.addVoltageRatioChangeListener(myBall);
            chLin2.addVoltageRatioChangeListener(myBall);

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
        background(200);

        /* Point A
        float addX = 0;
        float addY = 0;
        float size = 0;

        try {
            addX = (float) (width*chRota.getSensorValue());
            addY = (float) (height*chLin1.getSensorValue());
            size = (float) (height*chLin2.getSensorValue()/10);
        } catch (Exception e) {
            println(e.toString());
        }
        myBall.setSensorValues(addX,addY,size);
        // Point B */

        myBall.draw();
        //redBall.draw();

        boolean state = false;

        try {
            state = click.getState();
        } catch (Exception e){
            println(e.toString());
        }

        if(state){
            myBall.setBallColor(0,255,0);
        }


    }

    class Ministick implements VoltageRatioInputVoltageRatioChangeListener {
        float posX = 0;
        float posY = 0;
        float addX = 0;
        float addY = 0;
        float size = 10;

        int ballR = 0;
        int ballG = 0;
        int ballB = 0;

        int topLineR = 0;
        int topLineG = 0;
        int topLineB = 0;

        int botLineR = 0;
        int botLineG = 0;
        int botLineB = 0;


        Ministick(float x, float y) {
            this.posX = x;
            this.posY = y;
        }

        void draw() {
            //Draw ball where the joystick is
            strokeWeight(2);
            stroke(0,0,0);
            fill(ballR, ballG, ballB);
            float x = posX+addX;
            float y = posY+addY;
            ellipse(x, y, size, size);

            // Draw top line and update to green if over
            strokeWeight(4);
            stroke(topLineR, topLineG, topLineB);
            line((width/2) - 100, (height/2) - 100, (width/2)+100, (height/2) -100);
            if(y < ((height/2)-100)){
                setTopLineColor(0,255,0);
            }

            // Draw bottom line and update to green if below
            stroke(botLineR, botLineG, botLineB);
            line((width/2) - 100, (height/2) + 100, (width/2)+100, (height/2) +100);
            if(y > ((height/2)+100)){
                setBotLineColor(0,255,0);
            }
        }

        /*public void setSensorValues(float x, float y, float s) {
            this.addX = x;
            this.addY = y;
            this.size = s;
        }*/

        public void onVoltageRatioChange(VoltageRatioInputVoltageRatioChangeEvent rce) {
            try {
                if (rce.getSource().getChannel()==0) {
                    addX = (float) (rce.getVoltageRatio()*width/(2));
                    addX += (width/2) - addX;
                    //System.out.println(addX);
                }
                if (rce.getSource().getChannel()==1) {
                    addY = (float) rce.getVoltageRatio()*height/(2) + 158;
                    //System.out.println(addY);
                }
                if (rce.getSource().getChannel()==2) {
                    size = (float) rce.getVoltageRatio()*100;
                }
            } catch (PhidgetException e) {
                System.out.println(e.toString());
            }
        }

        void setBallColor(int r, int g, int b) {
            this.ballR = r;
            this.ballG = g;
            this.ballB = b;
        }

        void setTopLineColor(int r, int g, int b){
            this.topLineR = r;
            this.topLineG = g;
            this.topLineB = b;
        }

        void setBotLineColor(int r, int g, int b){
            this.botLineR = r;
            this.botLineG = g;
            this.botLineB = b;
        }
    }
}




