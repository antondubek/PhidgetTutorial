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

    private int r = 0;
    private int g = 191;
    private int b = 255;

    public Sound(PApplet p, Game game) {
        this.p = p;
        this.game = game;
    }

    public void draw(){
        p.noStroke();
        p.rectMode(CENTER);

        if(counter <= maxStacks) {
            for (float i = 1; i < counter; i++) {

                p.fill(p.lerpColor(p.color(240,255,0), p.color(255,0,0), ((i)/counter)));
                p.rect(((game.getWidth())/5) * 4, ((game.getHeight()/2)+140) - (i * 15), 140, 10);

            }
        } else {
            for (int i = 1; i < maxStacks; i++) {
                p.fill(0, 255, 0);
                p.rect(((game.getWidth())/5) * 4, ((game.getHeight()/2)+140) - (i * 15), 140, 10);

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
}
