import org.jdatepicker.impl.*;
import javax.swing.*;

import java.util.Date;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;

import com.formdev.flatlaf.json.ParseException;
import java.util.Properties;
import java.awt.*;
import java.text.SimpleDateFormat;

public class DateChooserComponent implements Component {
    private JDatePickerImpl datePicker;

    public DateChooserComponent(int x, int y, int w, int h) {

        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        datePicker.addActionListener(e -> {
            Date selectedDate = (Date) datePicker.getModel().getValue();
            if (selectedDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(selectedDate);
                datePicker.getJFormattedTextField().setText(formattedDate);
            }
        });

        datePicker.setBounds(x, y, w, h);
    }

    @Override
    public JComponent getComponent() {
        return datePicker;
    }

    

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
}
