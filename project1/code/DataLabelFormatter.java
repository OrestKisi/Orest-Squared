import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;

import com.formdev.flatlaf.json.ParseException;

class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy,MM,dd");

    @Override
    public Object stringToValue(String text) throws ParseException, java.text.ParseException {
        return dateFormatter.parse(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null && value instanceof Date) {
            return dateFormatter.format(value);
        }

        return "";
    }
}