import processing.core.PApplet;

public class Game extends PApplet {
    private PApplet p;

    private int backgroundR;
    private int backgroundG;
    private int backgroundB;
    private int height;
    private int width;

    public Ministick myBall;
    public Rotation myArc;
    public Click myClick;
    public Sound mySound;
    public Light myLight;


    public Game(PApplet p, int width, int height, int backgroundR, int backgroundG, int backgroundB) {
        this.p = p;
        this.backgroundR = backgroundR;
        this.backgroundG = backgroundG;
        this.backgroundB = backgroundB;
        this.height = height;
        this.width = width;

        Ministick myBall = new Ministick(p, this,0,0);
        Rotation myArc = new Rotation(p, this);
        Click myClick = new Click(p, this);
        Sound mySound = new Sound(p, this);
        Light myLight = new Light(p, this);
    }

    public void draw(){

    }

    public int getBackgroundR() {
        return backgroundR;
    }

    public int getBackgroundG() {
        return backgroundG;
    }

    public int getBackgroundB() {
        return backgroundB;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
