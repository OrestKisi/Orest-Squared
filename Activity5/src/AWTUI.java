/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *
 * Acknowledgements
 */

/*
 * AWT UI class used for displaying the information from the
 * associated weather station object.
 * This is an extension of JFrame, the outermost container in
 * a AWT application.
 */

import java.awt.* ;
import java.awt.event.* ;
import java.util.Observable ;
import java.util.Observer ;

import javax.swing.JLabel;

public class AWTUI extends Frame implements Observer {
    
    public Label celsiusField ;   // put current celsius reading here
    public Label kelvinField ;    // put current kelvin reading here
    public Label fahrenheitField;   //put current fahrenheit reading here
    public Label inchesField;   //put current inches reading here
    public Label millibarsField;    //put current millibars reading here

    /*
     * A Font object contains information on the font to be used to
     * render text.
     */
    private static Font labelFont =
        new Font(Font.SERIF, Font.PLAIN, 35) ;
    
    private final WeatherStation station ;

    
    /////////////////////////////////////////// Refactored code ///////////////////////////////////////////////////////
    
    public AWTUI(WeatherStation station) {
        
        super("Weather Station");

        this.station = station;
        this.station.addObserver(this);

        /*
        * WeatherStation frame is a grid of 1 row by an indefinite number
        * of columns.
        */
        setLayout(new GridLayout(1, 2)); // One row, two columns

        /*
         * setupTemperaturePanel to display the temperature for K and C.
         */
        setupTemperaturePanel("Kelvin", kelvinField = createTemperatureLabel());
        setupTemperaturePanel("Celsius", celsiusField = createTemperatureLabel());
        setupTemperaturePanel("Fahrenheit", fahrenheitField = createTemperatureLabel());
        setupTemperaturePanel("Inches", inchesField = createTemperatureLabel());
        setupTemperaturePanel("Millibars", millibarsField = createTemperatureLabel());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        pack();
        setVisible(true);
    }

    /*
     * method setupTemperaturePanel to display both temperatures
     */
    private void setupTemperaturePanel(String title, Label label) {
        Panel panel = new Panel(new GridLayout(2, 2));
        setLabel(title, panel);
        panel.add(label);
        add(panel);
    }

    /*
     * creates the temperature label
     */
    private Label createTemperatureLabel() {
        Label label = new Label("");
        label.setAlignment(Label.CENTER);
        label.setFont(labelFont);
        return label;
    }

    /*
     * sets the label font
     */

    private void setLabel(String title, Panel panel) {
        Label label = new Label(title);
        label.setAlignment(Label.CENTER);
        label.setFont(labelFont);
        panel.add(label);
    }

    /**
     * Station is the observable
     */

     public void update(Observable obs, Object ignore){
        if( station != obs ) {
            return ;
        }
        setKelvinLabel( station.getKelvin() ) ;
        setCelsiusLabel( station.getCelsius() ) ;
        setFahrenheitLabel( station.getFahrenheit() ) ; 
        setInchesLabel( station.getInches() ) ;
        setMillibarsLabel( station.getMillibars() ) ;
    }

    /*
     * Set the label holding the Kelvin temperature.
     */
    public void setKelvinLabel(double temperature) {
        kelvinField.setText(String.format("%6.2f", temperature)) ;
    }

    /*
     * Set the label holding the Celsius temperature.
     */
    public void setCelsiusLabel(double temperature) {
        celsiusField.setText(String.format("%6.2f", temperature)) ;
    }

    /*
      * Set the label holding the fahrenheit temperature.
      */

      public void setFahrenheitLabel(double temperature) {
        fahrenheitField.setText(String.format("%6.2f", temperature)) ;
    }

    /*
     * Set the label holding the Inches pressure.
     */

    public void setInchesLabel(double pressure) {
        inchesField.setText(String.format("%6.2f", pressure)) ;
    }

    /*
     * Set the label holding the Millibars pressure.
     */

    public void setMillibarsLabel(double pressure) {
        millibarsField.setText(String.format("%6.2f", pressure)) ;
    }

}


    // /*
    //  * Create and populate the AWTUI JFrame with panels and labels to
    //  * show the temperatures.
    //  */
    // public AWTUI() {
    //     super("Weather Station") ;

    //     /*
    //      * WeatherStation frame is a grid of 1 row by an indefinite number
    //      * of columns.
    //      */
    //     setLayout(new GridLayout(1,0)) ;

    //     /*
    //      * There are two panels, one each for Kelvin and Celsius, added to the
    //      * frame. Each Panel is a 2 row by 1 column grid, with the temperature
    //      * name in the first row and the temperature itself in the second row.
    //      */

    //     /*
    //      * Set up Kelvin display.
    //      */
    //     Panel panel = new Panel(new GridLayout(2,1)) ;
    //     add(panel) ;
    //     setLabel(" Kelvin ", panel) ;
    //     kelvinField = setLabel("", panel) ;

    //     /*
    //      * Set up Celsius display.
    //      */
    //     panel = new Panel(new GridLayout(2,1)) ;
    //     add(panel) ;
    //     setLabel(" Celsius ", panel) ;
    //     celsiusField = setLabel("", panel) ;

    //     /*
    //      * Set up the window's default close operation and pack its elements.
    //      */
    //     addWindowListener(
    //             new WindowAdapter() {
    //                 public void windowClosing(WindowEvent windowEvent){
    //                     System.exit(0);
    //                 }        
    //             });

    //     /*
    //      * Pack the components in this frame and make the frame visible.
    //      */
    //     pack() ;
    //     setVisible(true) ;
    // }

    /*
     * Create a Label with the initial value <title>, place it in
     * the specified <panel>, and return a reference to the Label
     * in case the caller wants to remember it.
     */


