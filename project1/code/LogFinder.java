import java.io.IOException;
import java.util.List;

public class LogFinder {
    private CSVWriter cw;
    private ComponentUtils sg;

    public LogFinder(ComponentUtils sg, CSVWriter cw) {
        this.cw = cw;
        this.sg = sg;
    }

    public void findLog() {
        try {
            // Get the year, month, and day from the user input
            String[] date = sg.getDateByLabelText("Log Date: ");

            // Ensure that the date array has three elements
            if (date.length == 3) {
                // Attempt to retrieve the log for the specified date
                Element logEntry = cw.getLogByDate(3, Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                        Integer.parseInt(date[2]));

                if (logEntry != null) {
                    // Display log details
                    StringBuilder logDetails = new StringBuilder();
                    logDetails.append("Your weight on ").append(date[0]).append("/").append(date[1]).append("/")
                            .append(date[2]).append(" was ").append(logEntry.toArray()[2]).append("kg\n");

                    // Retrieve consumption details for the specified date
                    Element consumptionEntry = cw.getLogByDate(4, Integer.parseInt(date[0]),
                            Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                    Element calorieLimit = cw.getLogByDate(5, Integer.parseInt(date[0]),
                            Integer.parseInt(date[1]), Integer.parseInt(date[2]));

                    List<ExercisePerformed> exPerfList = cw.getExercisesPerformedByDate(
                            Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                            Integer.parseInt(date[2]));

                    if (consumptionEntry != null) {
                        // Display consumption details
                        logDetails.append("\nYou have consumed:\n");

                        // Extracting consumption data from the consumption entry
                        String[] consumptionData = consumptionEntry.toArray();

                        for (int i = 3; i < consumptionData.length; i += 2) {
                            // Ensure we don't go out of bounds
                            if (i + 1 < consumptionData.length) {
                                String name = consumptionData[i];
                                Food food = (Food) cw.getByName(0, name);
                                logDetails.append(consumptionData[i + 1]).append("x - ").append(name).append("(")
                                        .append(food.toArray()[2]).append(" cal, ").append(food.toArray()[3])
                                        .append("g of fat, ").append(food.toArray()[4]).append("g of protein, ")
                                        .append(food.toArray()[5]).append(" carbohydrates)\n");
                            }
                        }
                    } else {
                        // Handle case where consumption for the given date was not found
                        logDetails.append("\nNo consumption recorded for the specified date.\n");
                    }

                    logDetails.append("\nDaily Calorie Limit: ");

                    if (calorieLimit != null) {
                        String[] calorieLimitData = calorieLimit.toArray();
                        logDetails.append(calorieLimitData[2]).append(" ccal\n");
                    } else {
                        // Handle case where consumption for the given date was not found
                        logDetails.append("2000.0 ccal\n");
                    }

                    if (exPerfList != null) {
                        // Display consumption details
                        logDetails.append("\nExercises Performed:\n");

                        // Extracting consumption data from the consumption entry
                        for (ExercisePerformed exercisesPerformed : exPerfList) {
                            // Access and process each exercise entry
                            String[] exercisesPerformedData = exercisesPerformed.toArray();
                            logDetails.append(exercisesPerformedData[2]).append(" was performed for ")
                                    .append(exercisesPerformedData[3]).append(" minutes\n");
                        }
                    } else {
                        // Handle case where consumption for the given date was not found
                        logDetails.append("\nNo exercises recorded for the specified date.\n");
                    }

                    sg.getJTextAreaByJLabelText("Description ").setText(logDetails.toString());

                } else {
                    // Handle case where log for the given date was not found
                    sg.getJTextAreaByJLabelText("Description ")
                            .setText("Log not found for the specified date.");
                }
            } else {
                // Handle case where user input for date is incomplete
                sg.getJTextAreaByJLabelText("Description ").setText("Please provide a complete date.");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
