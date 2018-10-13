import com.phidget22.VoltageRatioInputVoltageRatioChangeEvent;
import com.phidget22.VoltageRatioInputVoltageRatioChangeListener;
import processing.core.PApplet;

class Rotation extends PApplet implements VoltageRatioInputVoltageRatioChangeListener {

    private PApplet p;

    private float progress = 9;

    private float x;
    private float y;
    private float d;

    private int r = 0;
    private int g = 191;
    private int b = 255;

    private int innerR = 0;
    private int innerG = 191;
    private int innerB = 255;

    private int outerR = 0;
    private int outerG = 191;
    private int outerB = 255;

    private boolean lower = false;
    private boolean upper = false;

    private int backgroundR;
    private int backgroundG;
    private int backgroundB;
    private int height;
    private int width;

    public Rotation(PApplet p, int width, int height, int backgroundR, int backgroundG, int backgroundB){
        this.p = p;
        this.width = width;
        this.height = height;
        this.x = width/6;
        this.y = height/6;
        this.d = (float) (width * 0.2);
        this.backgroundR = backgroundR;
        this.backgroundG = backgroundG;
        this.backgroundB = backgroundB;

    }

    public void draw(){

        // Outer circle
        p.ellipseMode(CENTER);
        p.stroke(outerR,outerG,outerB);
        p.fill(r, g, b, 50);
        p.ellipse(x, y, d, d);

        showArcs();

        // Inner circle
        p.stroke(innerR,innerG,innerB);
        p.fill(51, 51, 51);
        p.ellipse(x, y, d-30, d-30);

        if(progress < 0.5){
            setInner(0,255,0);
            lower = true;
        }

        if(progress > 134){
            setOuter(0,255,0);
            upper = true;
        }

        if(lower && upper){
            setArc(0,255,0);
        }


    }

    @Override
    public void onVoltageRatioChange(VoltageRatioInputVoltageRatioChangeEvent lol) {
        //Add to progress
        float input = (float) (lol.getVoltageRatio());
        progress =  (float) ((input-0.0)/(0.999 - 0.0) * (135) + 0);
    }

    private void showArcs() {
        p.stroke(r,g,b);
        p.fill(r, g, b, 200);
        p.arc(x, y, d, d, PI+HALF_PI, map(progress, 0, 100, PI+HALF_PI, PI+HALF_PI+PI+HALF_PI));
    }

    private void setOuter(int r, int g, int b){
        this.outerR = r;
        this.outerG = g;
        this.outerB = b;
    }

    private void setInner(int r, int g, int b){
        this.innerR = r;
        this.innerG = g;
        this.innerB = b;
    }

    private void setArc(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }


}