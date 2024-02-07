public interface ITempSensor {
    final int MINREADING = 23315 ;
    final int MAXREADING = 38315 ;
    final int DEFAULT = 29315 ;

    public double getCelsius();
}
