/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *
 * Acknowledgements
 */

/*
 * Swing UI class used for displaying the information from the
 * associated weather station object.
 * This is an extension of JFrame, the outermost container in
 * a Swing application.
 */

 import java.awt.Font ;
 import java.awt.GridLayout ;
 import java.util.Observable ;
 import java.util.Observer ;
 
 import javax.swing.JFrame ;
 import javax.swing.JLabel ;
 import javax.swing.JPanel ;
 
 //import java.text.DecimalFormat ;
 
 public class SwingUI extends JFrame implements Observer{
     private JLabel celsiusField ;   // put current celsius reading here
     private JLabel kelvinField ;    // put current kelvin reading here
     private JLabel fahrenheitField;
     private JLabel inchesField;
     private JLabel millibarsField;
     
     /*
      * A Font object contains information on the font to be used to
      * render text.
      */
     private static Font labelFont =
         new Font(Font.SERIF, Font.PLAIN, 40) ;
 
     private final WeatherStation station ;
 
     /*
      * Create and populate the SwingUI JFrame with panels and labels to
      * show the temperatures.
      */
     public SwingUI(WeatherStation station) {
         super("Weather Station") ;
         this.setLocation(0,150);
 
         this.station = station;
         this.station.addObserver(this);
 
         /*
          * WeatherStation frame is a grid of 1 row by an indefinite
          * number of columns.
          */
         this.setLayout(new GridLayout(1,0)) ;
 
         setupTemperaturePanel("Kelvin", kelvinField = createTemperatureLabel());
         setupTemperaturePanel("Celsius", celsiusField = createTemperatureLabel());
         setupTemperaturePanel("Fahrenheit", fahrenheitField = createTemperatureLabel());
         setupTemperaturePanel("Inches", inchesField = createTemperatureLabel());
         setupTemperaturePanel("Millibars", millibarsField = createTemperatureLabel());

 
         /*
          * There are two panels, one each for Kelvin and Celsius, added to the
          * frame. Each Panel is a 2 row by 1 column grid, with the temperature
          * name in the first row and the temperature itself in the second row.
          */
 
          /*
          * Set up the frame's default close operation pack its elements,
          * and make the frame visible.
          */
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
         this.pack() ;
         this.setVisible(true) ;
     }
 
     /////////////////////////////////////////// Refactored code ///////////////////////////////////////////////////////
 
     /*
      * method setupTemperaturePanel to display both temperatures
      */
     private void setupTemperaturePanel(String title, JLabel label) {
         JPanel panel = new JPanel(new GridLayout(2, 2));
         createLabel(title, panel);
         panel.add(label);
         add(panel);
         
     }
 
     /*
      * creates the temperature label
      */
     
     private JLabel createTemperatureLabel(){
         JLabel label = new JLabel("");
         label.setHorizontalAlignment(JLabel.CENTER);
         label.setVerticalAlignment(JLabel.TOP);
         label.setFont(labelFont);
         return label;
     }
 
     /**
      * Station is the observable
      */
 
     public void update(Observable obs, Object ignore){
         if( station != obs ) {
             return ;
         }
         setKelvinJLabel( station.getKelvin() ) ;
         setCelsiusJLabel( station.getCelsius() ) ;
         setFahrenheitJLabel( station.getFahrenheit() ) ; 
         setInchesJLabel( station.getInches() ) ;
         setMillibarsJLabel( station.getMillibars() ) ;
     }
 
     /*
      * Set the label holding the Kelvin temperature.
      */
     public void setKelvinJLabel(double temperature) {
         kelvinField.setText(String.format("%6.2f", temperature)) ;
     }
 
     /*
      * Set the label holding the Celsius temperature.
      */
     public void setCelsiusJLabel(double temperature) {
         celsiusField.setText(String.format("%6.2f", temperature)) ;
     }

     /*
      * Set the label holding the fahrenheit temperature.
      */

     public void setFahrenheitJLabel(double temperature) {
        fahrenheitField.setText(String.format("%6.2f", temperature)) ;
    }

    /*
     * Set the label holding the Inches pressure.
     */

    public void setInchesJLabel(double pressure) {
        inchesField.setText(String.format("%6.2f", pressure)) ;
    }

    /*
     * Set the label holding the Millibars pressure.
     */

    public void setMillibarsJLabel(double pressure) {
        millibarsField.setText(String.format("%6.2f", pressure)) ;
    }
 
     /*
      * Create a Label with the initial value <title>, place it in
      * the specified <panel>, and return a reference to the Label
      * in case the caller wants to remember it.
      */
     private JLabel createLabel(String title, JPanel panel) {
         JLabel label = new JLabel(title) ;
         label.setHorizontalAlignment(JLabel.CENTER) ;
         label.setVerticalAlignment(JLabel.TOP) ;
         label.setFont(labelFont) ;
         panel.add(label) ;
 
         return label ;
     }
 }
 
