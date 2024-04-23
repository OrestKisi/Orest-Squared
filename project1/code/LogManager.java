import java.io.IOException;
import java.util.List;

public class LogManager {
    private final CSVWriter cw;
    private final ComponentUtils sg;

    public LogManager(ComponentUtils sg, CSVWriter cw) {
        this.cw = cw;
        this.sg = sg;
    }


    public List<String> addProductsToLog(List<String> _products) {

        List<String> products = _products;

        // combobox by label text

        // Get the selected item in the combobox
        String selectedProduct = (String) sg.getJComboBoxByJLabelText("Product Consumed:  ").getSelectedItem();

        if (!selectedProduct.equals("Other")) {
            // Search for the selected product in food.csv and add it to the recipe and
            // textarea

            try {
                Element productElement = cw.getByName(0, selectedProduct); // Assuming 0 is the id for
                                                                           // products in food.csv

                if (productElement != null) {

                    if (sg.getJTextAreaByJLabelText("Current:  ").getText().contains("Log with date ")) {
                        sg.getJTextAreaByJLabelText("Current:  ").setText("");
                    }

                    Food selectedFood = (Food) productElement;
                    sg.getJTextAreaByJLabelText("Current:  ")
                            .append("1x - " + selectedFood.toArray()[1] + "("
                                    + selectedFood.toArray()[2] + "cal, "
                                    + selectedFood.toArray()[3] + "g of fat, "
                                    + selectedFood.toArray()[4] + "g of protein, "
                                    + selectedFood.toArray()[5] + " carbohydrates.\n");
                    products.add(selectedFood.toArray()[1]);
                    products.add(1 + "");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {

            // OTHER
            // Adding product to textarea
            if (sg.getJTextAreaByJLabelText("Current:  ").getText().contains("Log with date ")) {
                sg.getJTextAreaByJLabelText("Current:  ").setText("");
            }

            sg.getJTextAreaByJLabelText("Current:  ")
                    .append("1x - " + sg.getJTextFieldByJLabelText("Product Name:  ").getText() + "("
                            + Integer.parseInt(sg.getJTextFieldByJLabelText("Calories:  ").getText()) + "cal, "
                            + Integer.parseInt(sg.getJTextFieldByJLabelText("Fat(g):  ").getText())
                            + "g of fat, "
                            + Integer.parseInt(sg.getJTextFieldByJLabelText("Protein(g):  ").getText())
                            + "g of protein, "
                            + Integer.parseInt(sg.getJTextFieldByJLabelText("Carbohydrates:  ").getText())
                            + " carbohydrates.");
            try {
                // Adding product to food.csv
                cw.write(new Food(sg.getJTextFieldByJLabelText("Product Name:  ").getText(),
                        Integer.parseInt(sg.getJTextFieldByJLabelText("Calories:  ").getText()),
                        Integer.parseInt(sg.getJTextFieldByJLabelText("Fat(g):  ").getText()),
                        Integer.parseInt(sg.getJTextFieldByJLabelText("Carbohydrates:  ").getText()),
                        Integer.parseInt(sg.getJTextFieldByJLabelText("Protein(g):  ").getText())));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            products.add(sg.getJTextFieldByJLabelText("Product Name:  ").getText());
            products.add(1 + "");
        }

        return products;
        
    }





    public List<String> addLog(List<String> _products) {

        List<String> products = _products;

        String selectedExercise = (String) sg.getJComboBoxByJLabelText("Exercise Performed:").getSelectedItem();

        String[] str = sg.getDateByLabelText("Date:  ");
        double weight = Double.parseDouble(sg.getJTextFieldByJLabelText("Current weight:  ").getText());

        double calorieLimit = Double.parseDouble(sg.getJTextFieldByJLabelText("Calorie Limit: ").getText());

        double timeSpent = Double.parseDouble(sg.getJTextFieldByJLabelText("Time Spent: ").getText());

        String exerciseName = "";
        double caloriesBurn = 0;

        Consumption consumption = new Consumption(Integer.parseInt(str[0]), Integer.parseInt(str[1]),
                Integer.parseInt(str[2]), products);
        Log log = new Log(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]),
                weight);

        CalorieLimit cl = new CalorieLimit(Integer.parseInt(str[0]), Integer.parseInt(str[1]),
                Integer.parseInt(str[2]), calorieLimit);

        try {

            if (selectedExercise.equals("Other ")) {

                exerciseName = sg.getJTextFieldByJLabelText("Excersice Name: ").getText();
                caloriesBurn = Double
                        .parseDouble(sg.getJTextFieldByJLabelText("Calories Burn(ccal/min): ").getText());

                ExercisePerformed ef = new ExercisePerformed(Integer.parseInt(str[0]),
                        Integer.parseInt(str[1]), Integer.parseInt(str[2]), exerciseName, timeSpent);
                Exercise ex = new Exercise(exerciseName, caloriesBurn);
                cw.write(ef);
                cw.write(ex);
            } else {

                exerciseName = selectedExercise;

                ExercisePerformed ef = new ExercisePerformed(Integer.parseInt(str[0]),
                        Integer.parseInt(str[1]), Integer.parseInt(str[2]), exerciseName, timeSpent);
                cw.write(ef);

            }

            cw.write(log);
            cw.write(consumption);
            cw.write(cl);

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        sg.getJTextAreaByJLabelText("Current:  ")
                .setText("Log with date " + str[0] + "/" + str[1] + "/" + str[2] + " added to log.csv");
        products.clear();

        return products;
    }
}
