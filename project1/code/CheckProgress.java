import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JTextArea;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.chart.plot.XYPlot;

public class CheckProgress {

    private ComponentUtils sg;
    private CSVWriter cw;

    public CheckProgress(ComponentUtils _sg, CSVWriter _cw) {
        sg = _sg;
        cw = _cw;
    }

    public void checkProgress() {
        try {
            LocalDate from = getDateFromUserInput("From: ");
            LocalDate to = getDateFromUserInput("To: ");
            TimeSeries seriesTotalCal = new TimeSeries("Total Calories Consumed");
            TimeSeries seriesTotalBurnt = new TimeSeries("Total Calories Expended");
            TimeSeriesCollection dataset = new TimeSeriesCollection();
            dataset.addSeries(seriesTotalCal);
            dataset.addSeries(seriesTotalBurnt);
            StringBuilder logEntries = new StringBuilder();
            for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
                processLogEntry(date, seriesTotalCal, seriesTotalBurnt, logEntries);
            }
            updateChartDataset(dataset);
            displayLogEntries(logEntries);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (NumberFormatException e2) {
            handleInvalidDateFormat();
        }
    }

    private LocalDate getDateFromUserInput(String labelText) throws IOException {
        String[] date = sg.getDateByLabelText(labelText);
        return LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
    }

    private void processLogEntry(LocalDate date, TimeSeries seriesTotalCal, TimeSeries seriesTotalBurnt,
            StringBuilder logEntries) throws IOException {
        Element logEntry = cw.getLogByDate(3, date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        Element calLimEntry = cw.getLogByDate(5, date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        List<ExercisePerformed> exPerfEntryList = cw.getExercisesPerformedByDate(date.getYear(),
                date.getMonthValue(), date.getDayOfMonth());
        Element consumptionEntry = cw.getLogByDate(4, date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        if (logEntry != null) {
            logEntries.append(date.toString()).append(" - Weight: ").append(logEntry.toArray()[2])
                    .append("kg, Limit: ").append(calLimEntry.toArray()[2])
                    .append("cal").append("\n");
            String[] consumptionData = consumptionEntry.toArray();
            double totalCal = 0.0;
            for (int i = 3; i < consumptionData.length; i += 2) {
                if (i + 1 < consumptionData.length) {
                    String name = consumptionData[i];
                    Food food = (Food) cw.getByName(0, name);
                    totalCal += Double.parseDouble(food.toArray()[2]);
                }
            }
            if (totalCal != 0.0) {
                seriesTotalCal.add(new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear()), totalCal);
            }
            logEntries.append("   - Total calories from consumption: " + totalCal + "cal\n");
            double totalBurnt = 0.0;
            for (int i = 0; i < exPerfEntryList.size(); i++) {
                ExercisePerformed exPerfEntry = exPerfEntryList.get(i);
                String exerciseName = exPerfEntry.toArray()[2];
                Exercise exercise = (Exercise) cw.getByName(2, exerciseName);
                if (exercise != null) {
                    double cal = Double.parseDouble(exercise.toArray()[2]);
                    double time = Double.parseDouble(exPerfEntry.toArray()[3]);
                    double weight = Double.parseDouble(logEntry.toArray()[2]);
                    totalBurnt += cal * (weight / 100.0) * (time / 60.0);
                }
            }
            if (totalBurnt != 0.0) {
                seriesTotalBurnt.add(new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear()), totalBurnt);
            }
            logEntries.append("   - Total calories expended from exercises: " + round(totalBurnt, 1) + "cal\n");
            logEntries.append("Result:\n   - ");
            if (totalCal <= totalBurnt) {
                double difference = totalBurnt - totalCal;
                logEntries.append("You have lost ").append(round(difference, 1)).append("cal");
            } else {
                double difference = totalCal - totalBurnt;
                logEntries.append("You have gained ").append(round(difference, 1)).append("cal");
            }
            logEntries.append("\n\n");
        } else {
            logEntries.append(date.toString()).append(" - No log entry\n\n");
        }
    }

    private void updateChartDataset(TimeSeriesCollection dataset) {
        XYPlot plot = (XYPlot) sg.getChartPanel().getChart().getPlot();
        plot.setDataset(dataset);
    }

    private void displayLogEntries(StringBuilder logEntries) {
        JTextArea ta = sg.getJTextAreaByJLabelText("Description:   ");
        ta.setText(logEntries.toString());
    }

    private void handleInvalidDateFormat() {
        JTextArea ta = sg.getJTextAreaByJLabelText("Description ");
        ta.setText("Invalid date format. Please enter numbers for year, month, and day.");
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
