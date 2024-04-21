import java.awt.event.ItemEvent;
import java.util.List;
import javax.swing.JTabbedPane;

public class ApplicationFacade {

    private final ComponentUtils cu;
    private final CheckProgress cp;
    private final LogFinder lf;
    private final RecipeFinder rf;
    private final LogManager lm;
    private final RecipeManager rm;
    private final ExerciseManager em;
    
    public ApplicationFacade(JTabbedPane jTabbedPane, CSVWriter cw) {

        cu = new ComponentUtils(jTabbedPane); // Assuming tabPanel is the content pane of the window

        cp = new CheckProgress(cu, cw);
        lf = new LogFinder(cu, cw);
        rf = new RecipeFinder(cu, cw);
        lm = new LogManager(cu, cw);
        rm = new RecipeManager(cu, cw);
        em = new ExerciseManager(cu, cw);
    }

    public List<String> addProductsToLog(List<String> products) {
        return lm.addProductsToLog(products);
    }

    public List<String> addProductsToRecipe(List<String> products) {
        return rm.addProductsToRecipe(products);
    }

    public List<String> addRecipe(List<String> products) {
        return rm.addRecipe(products);
    }

    public List<String> addLog(List<String> products) {
        return lm.addLog(products);
    }

    public void findRecipe() {
        rf.findRecipe();
    }

    public void findLog() {
        lf.findLog();
    }

    public void checkProgress() {
        cp.checkProgress();
    }

    public void addExercise() {
        em.addExercise();
    }

    public void changeItems(ItemEvent e){
        cu.changeItems(e);
    }
}
