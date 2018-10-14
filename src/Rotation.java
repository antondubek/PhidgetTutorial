import com.phidget22.VoltageRatioInputVoltageRatioChangeEvent;
import com.phidget22.VoltageRatioInputVoltageRatioChangeListener;
import processing.core.PApplet;

class Rotation extends PApplet implements VoltageRatioInputVoltageRatioChangeListener {

    private PApplet p;
    private Game game;
    private boolean solved = false;

    private float progress = 9;

    private float x;
    private float y;
    private float d;

    private int r = 255;
    private int g = 0;
    private int b = 128;

    private int innerR = 255;
    private int innerG = 0;
    private int innerB = 128;

    private int outerR = 255;
    private int outerG = 0;
    private int outerB = 128;

    private int textR = 255;
    private int textG = 255;
    private int textB = 255;

    private boolean lower = false;
    private boolean upper = false;


    public Rotation(PApplet p, Game game){
        this.p = p;
        this.x = (game.getWidth()/5) * 2;
        this.y = game.getHeight()/2;
        this.d = (float) (game.getHeight() *0.4);
        this.game = game;
    }

    public void draw(){
        p.fill(textR,textG,textB);
        p.stroke(textR, textG,textB);
        p.text("Twist IT", (game.getWidth()/5) * 2, (game.getHeight()/10) * 8);
        // Outer circle
        p.ellipseMode(CENTER);
        p.stroke(outerR,outerG,outerB);
        p.fill(r, g, b, 50);
        p.ellipse(x, y, d, d);

        showArcs();

        // Inner circle
        p.stroke(innerR,innerG,innerB);
        p.fill(game.getBackgroundR(), game.getBackgroundG(), game.getBackgroundB());
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
            setTextColor(0,255,0);
            solved = true;
        }

    }

    @Override
    public void onVoltageRatioChange(VoltageRatioInputVoltageRatioChangeEvent event) {
        //Add to progress
        float input = (float) (event.getVoltageRatio());
        progress =  (float) ((input-0.0)/(0.999 - 0.0) * (140) + 0);
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

    private void setTextColor(int r, int g, int b){
        this.textR = r;
        this.textG = g;
        this.textB = b;
    }

    public boolean isSolved() {
        return solved;
    }
}