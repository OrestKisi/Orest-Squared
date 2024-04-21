import java.io.IOException;

public class RecipeFinder {
    private final CSVWriter cw;
    private final ComponentUtils sg;

    public RecipeFinder(ComponentUtils sg, CSVWriter cw) {
        this.cw = cw;
        this.sg = sg;
    }

    public void findRecipe() {
        try {
            String recipeTitle = sg.getJTextFieldByJLabelText("Recipe Title:  ").getText();
            Element recipeElement = cw.getByName(1, recipeTitle);

            if (recipeElement != null) {
                StringBuilder recipeDetails = new StringBuilder();
                recipeDetails.append("Recipe Title: ").append(recipeTitle).append("\nRecipe Contains:\n");

                for (int i = 3; i < recipeElement.toArray().length; i += 2) {
                    String foodName = recipeElement.toArray()[i];
                    Food food = (Food) cw.getByName(0, foodName);

                    recipeDetails.append("    ").append(recipeElement.toArray()[i + 1]).append("x - ").append(foodName)
                            .append("(").append(food.toArray()[2]).append(" cal, ").append(food.toArray()[3])
                            .append(" g of fat, ").append(food.toArray()[4]).append(" g of protein, ")
                            .append(food.toArray()[5]).append(" carbohydrates)\n");
                }

                sg.getJTextAreaByJLabelText("Description:  ").setText(recipeDetails.toString());
            } else {
                // Handle case where recipe with the given title was not found
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
