import com.phidget22.PhidgetException;
import com.phidget22.VoltageRatioInputVoltageRatioChangeEvent;
import com.phidget22.VoltageRatioInputVoltageRatioChangeListener;
import processing.core.PApplet;

class Ministick extends PApplet implements VoltageRatioInputVoltageRatioChangeListener {

    private PApplet p;

    private float posX = 0;
    private float posY = 0;
    private float addX = 0;
    private float addY = 0;
    private float size = 10;

    private int ballR = 0;
    private int ballG = 191;
    private int ballB = 255;

    private int topLineR = 0;
    private int topLineG = 191;
    private int topLineB = 255;

    private int botLineR = 0;
    private int botLineG = 191;
    private int botLineB = 255;

    private boolean top = false;
    private boolean bottom = false;

    private int height;
    private int width;
    private Game game;


    Ministick(PApplet p, Game game, float x, float y) {
        this.p = p;
        this.game = game;
        this.posX = x;
        this.posY = y;
        this.height = game.getHeight();
        this.width = game.getWidth();
    }

    public void draw() {

        //Draw ball where the joystick is
        p.strokeWeight(2);
        p.stroke(0,0,0);
        p.fill(ballR, ballG, ballB);
        float x = posX+addX;
        float y = posY+addY;
        p.ellipse(x, y, size, size);

        // Draw top line and update to green if over
        p.strokeWeight(4);
        p.stroke(topLineR, topLineG, topLineB);
        p.line((width/2) - 100, (height/2) - 100, (width/2)+100, (height/2) -100);
        if(y < ((height/2)-100)){
            setTopLineColor(0,255,0);
            top = true;
        }

        // Draw bottom line and update to green if below
        p.stroke(botLineR, botLineG, botLineB);
        p.line((width/2) - 100, (height/2) + 100, (width/2)+100, (height/2) +100);
        if(y > ((height/2)+100)){
            setBotLineColor(0,255,0);
            bottom = true;
        }

        if(top & bottom){
            setBallColor(0,255,0);
        }


    }

    public void onVoltageRatioChange(VoltageRatioInputVoltageRatioChangeEvent rce) {
        try {
            if (rce.getSource().getChannel()==1) {
                addX = (float) (rce.getVoltageRatio()*width/(2));
                addX += (width/2) - addX;
                //System.out.println(addX);
            }
            if (rce.getSource().getChannel()==2) {
                addY = (float) rce.getVoltageRatio()*height/(2) + 158;
                //System.out.println(addY);
            }
        } catch (PhidgetException e) {
            System.out.println(e.toString());
        }
    }

    private void setBallColor(int r, int g, int b) {
        this.ballR = r;
        this.ballG = g;
        this.ballB = b;
    }

    private void setTopLineColor(int r, int g, int b){
        this.topLineR = r;
        this.topLineG = g;
        this.topLineB = b;
    }

    private void setBotLineColor(int r, int g, int b){
        this.botLineR = r;
        this.botLineG = g;
        this.botLineB = b;
    }
}
