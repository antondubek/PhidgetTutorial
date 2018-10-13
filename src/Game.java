public class Game {

    private int backgroundR;
    private int backgroundG;
    private int backgroundB;
    private int height;
    private int width;


    public Game(int width, int height, int backgroundR, int backgroundG, int backgroundB) {
        this.backgroundR = backgroundR;
        this.backgroundG = backgroundG;
        this.backgroundB = backgroundB;
        this.height = height;
        this.width = width;
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
