import com.phidget22.VoltageInputVoltageChangeEvent;
import com.phidget22.VoltageInputVoltageChangeListener;
import processing.core.PApplet;

public class Sound extends PApplet implements VoltageInputVoltageChangeListener {

    private PApplet p;
    private Game game;
    private boolean solved = false;

    private int maxStacks = 20;
    private int counter = 1;
    private int display = 0;

    private int textR = 255;
    private int textG = 255;
    private int textB = 255;

    private int height;
    private int width;

    public Sound(PApplet p, Game game) {
        this.p = p;
        this.game = game;
        this.height = game.getHeight();
        this.width = game.getWidth();
    }

    public void draw(){
        p.fill(textR,textG,textB);
        p.stroke(textR, textG,textB);
        p.text("Clap IT", (width/5) * 4, (height/10) * 8);

        p.noStroke();
        p.rectMode(CENTER);

        if(counter <= maxStacks && !isSolved()) {
            for (float i = 1; i < counter; i++) {

                p.fill(p.lerpColor(p.color(240,255,0), p.color(255,0,0), ((i)/counter)));
                p.rect(((width)/5) * 4, ((height/2)+140) - (i * 15), 140, 10);

            }
        } else {
            for (int i = 1; i < maxStacks; i++) {
                p.fill(0, 255, 0);
                p.rect(((width)/5) * 4, ((height/2)+140) - (i * 15), 140, 10);
                setTextColor(0,255,0);
                //Debug this to only produce 1 incrmenent not 300
                this.display++;
                solved = true;
            }
        }
    }

    public boolean isSolved() {
        return solved;
    }

    @Override
    public void onVoltageChange(VoltageInputVoltageChangeEvent event) {
        double input = event.getVoltage() * 1000;

        // USE FOR REAL THING
        //counter =  (int) (1 + (input - 60)*(20-1)/(400-60));

        // USE FOR LATE NIGHT TESTING
        counter =  (int) (1 + (input - 60)*(20-1)/(200-60));
    }

    private void setTextColor(int r, int g, int b){
        this.textR = r;
        this.textG = g;
        this.textB = b;
    }
}
