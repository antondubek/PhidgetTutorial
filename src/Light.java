import com.phidget22.VoltageRatioInputVoltageRatioChangeEvent;
import com.phidget22.VoltageRatioInputVoltageRatioChangeListener;
import processing.core.PApplet;

public class Light extends PApplet implements VoltageRatioInputVoltageRatioChangeListener {

    private PApplet p;
    private Game game;
    private boolean solved;

    private float sensorReading;

    public Light(PApplet p, Game game) {
        this.p = p;
        this.game = game;
    }

    public boolean isSolved() {
        return solved;
    }

    @Override
    public void onVoltageRatioChange(VoltageRatioInputVoltageRatioChangeEvent event) {
        //Add to progress
        sensorReading = (float) (event.getVoltageRatio()) * 1000;
    }

    public float getSensorReading() {
        return sensorReading;
    }
}
