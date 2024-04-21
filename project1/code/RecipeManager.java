import java.io.IOException;
import java.util.List;

public class RecipeManager {
    private final CSVWriter cw;
    private final ComponentUtils sg;

    public RecipeManager(ComponentUtils sg, CSVWriter cw) {
        this.cw = cw;
        this.sg = sg;
    }

    public List<String> addProductsToRecipe(List<String> _products) {

        List<String> products = _products;

        String selectedProduct = (String) sg.getJComboBoxByJLabelText("Product: ").getSelectedItem();

        if (!selectedProduct.equals("Other")) {
            // Search for the selected product in food.csv and add it to the recipe and
            // textarea

            try {
                Element productElement = cw.getByName(0, selectedProduct); // Assuming 0 is the id for
                                                                           // products in food.csv

                if (productElement != null) {

                    if (sg.getJTextAreaByJLabelText("Current: ").getText()
                            .contains(" recipe added to food.csv")) {
                                sg.getJTextAreaByJLabelText("Current: ").setText("");
                    }

                    Food selectedFood = (Food) productElement;
                    sg.getJTextAreaByJLabelText("Current: ")
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
            // Continue with the code under the //OTHER comment
            // Adding product to textarea

            if (sg.getJTextAreaByJLabelText("Current: ").getText().contains(" recipe added to food.csv")) {
                sg.getJTextAreaByJLabelText("Current: ").setText("");
            }

            sg.getJTextAreaByJLabelText("Current: ")
                    .append("1x - " + sg.getJTextFieldByJLabelText("Product Name: ").getText() + "("
                            + sg.getJTextFieldByJLabelText("Calories: ").getText() + "cal, "
                            + sg.getJTextFieldByJLabelText("Fat(g): ").getText() + "g of fat, "
                            + sg.getJTextFieldByJLabelText("Protein(g): ").getText() + "g of protein, "
                            + sg.getJTextFieldByJLabelText("Carbohydrates: ").getText() + " carbohydrates.\n");
            try {
                // Adding product to food.csv
                cw.write(new Food(sg.getJTextFieldByJLabelText("Product Name: ").getText(),
                        Integer.parseInt(sg.getJTextFieldByJLabelText("Calories: ").getText()),
                        Integer.parseInt(sg.getJTextFieldByJLabelText("Fat(g): ").getText()),
                        Integer.parseInt(sg.getJTextFieldByJLabelText("Carbohydrates: ").getText()),
                        Integer.parseInt(sg.getJTextFieldByJLabelText("Protein(g): ").getText())));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            products.add(sg.getJTextFieldByJLabelText("Product Name: ").getText());
            products.add(1 + "");
        }

        return products;

    }

    public List<String> addRecipe(List<String> _products) {

        List<String> products = _products;

        sg.getJTextAreaByJLabelText("Current: ").setText("\""
                + sg.getJTextFieldByJLabelText("Recipe Title: ").getText() + "\" recipe added to food.csv");
        try {
            cw.write(new Recipe(sg.getJTextFieldByJLabelText("Recipe Title: ").getText(), products));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        products.clear();

        return products;
    }
}
