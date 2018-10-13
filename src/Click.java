import com.phidget22.DigitalInputStateChangeEvent;
import com.phidget22.DigitalInputStateChangeListener;
import processing.core.PApplet;

public class Click extends PApplet implements DigitalInputStateChangeListener {

    private PApplet p;
    private Game game;
    private boolean solved;

    private int maxStacks = 20;
    private int counter = 1;
    private int helper = 0;

    private int r = 138;
    private int g = 20;
    private int b = 246;

    public Click(PApplet p, Game game) {
        this.p = p;
        this.game = game;
    }

    public void draw(){
        p.noStroke();
        p.rectMode(CENTER);

        if(counter <= maxStacks) {
            for (int i = 1; i < counter; i++) {
                p.fill(r, g, b);
                p.rect(((game.getWidth())/5) * 3, ((game.getHeight()/2)+140) - (i * 15), 140, 10);

            }
        } else {
            for (int i = 1; i < maxStacks; i++) {
                p.fill(0, 255, 0);
                p.rect(((game.getWidth())/5) * 3, ((game.getHeight()/2)+140) - (i * 15), 140, 10);

                solved = true;
            }
        }
    }

    @Override
    public void onStateChange(DigitalInputStateChangeEvent event) {
        helper++;
        if(helper % 2 == 0){
            counter++;
        }
    }

    public boolean isSolved() {
        return solved;
    }
}
