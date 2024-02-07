public class KelvinTempSensorAdapter extends KelvinTempSensor implements ITempSensor{

    private KelvinTempSensor kts = new KelvinTempSensor();
    private final int K2C_CONVERT = -27315;
    
    public double getCelsius(){

        return (kts.reading() + K2C_CONVERT) / 100.0;
        
    }
    
    
}
