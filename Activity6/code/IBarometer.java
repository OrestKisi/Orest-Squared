public interface IBarometer {
    final double MIN = 27.0 ;       // minimum reading
    final double MAX = 32.0 ;       // maximum reading
    final double DEFAULT = 29.92 ;  // default reading.

    public double pressure();
}

