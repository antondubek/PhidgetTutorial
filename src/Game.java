public class Game {

    public static int backgroundR;
    public static int backgroundG;
    public static int backgroundB;
    public static int height;
    public static int width;


    public Game(int width, int height, int backgroundR, int backgroundG, int backgroundB) {
        this.backgroundR = backgroundR;
        this.backgroundG = backgroundG;
        this.backgroundB = backgroundB;
        this.height = height;
        this.width = width;
    }

    public static int getBackgroundR() {
        return backgroundR;
    }

    public static int getBackgroundG() {
        return backgroundG;
    }

    public static int getBackgroundB() {
        return backgroundB;
    }

    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }
}
