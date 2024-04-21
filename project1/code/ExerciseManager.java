import java.io.IOException;

public class ExerciseManager {
    private CSVWriter cw;
    private ComponentUtils sg;

    public ExerciseManager(ComponentUtils sg, CSVWriter cw) {
        this.cw = cw;
        this.sg = sg;
    }

    public void addExercise() {
        try {
            // Adding exercise to exercise.csv
            cw.write(new Exercise(sg.getJTextFieldByJLabelText("Exercise Title: ").getText(),
                    Integer.parseInt(sg.getJTextFieldByJLabelText("Calories:   ").getText())));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
