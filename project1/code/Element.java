import java.util.ArrayList;
import java.util.List;

abstract class Element {
    abstract String[] toArray();
}

class Food extends Element {
    private String name;
    private int ccal, fat, carbohydrates, protein;

    public Food(String name, int ccal, int fat, int carbohydrates, int protein) {
        this.name = name;
        this.ccal = ccal;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
    }

    @Override
    public String[] toArray() {
        return new String[]{"b", name, String.valueOf(ccal), String.valueOf(fat),
                String.valueOf(protein), String.valueOf(carbohydrates)};
    }
}

class Recipe extends Element {
    private String name;
    private List<String> ingredients;

    public Recipe(String name, List<String> ingredients) {
        this.name = name;
        this.ingredients = new ArrayList<>(ingredients);
    }

    @Override
    public String[] toArray() {
        List<String> arrList = new ArrayList<>();
        arrList.add("r");
        arrList.add(name);
        arrList.addAll(ingredients);
        return arrList.toArray(new String[0]);
    }
}

class Log extends Element {
    private String date;
    private double weight;

    public Log(int year, int month, int day, double weight) {
        this.date = String.format("%d,%d,%d", year, month, day);
        this.weight = weight;
    }

    @Override
    public String[] toArray() {
        return new String[]{date, "w", String.valueOf(weight)};
    }
}

class Consumption extends Element {
    private String date;
    private List<String> items;

    public Consumption(int year, int month, int day, List<String> items) {
        this.date = String.format("%d,%d,%d", year, month, day);
        this.items = new ArrayList<>(items);
    }

    @Override
    public String[] toArray() {
        List<String> arrList = new ArrayList<>();
        arrList.add(date);
        arrList.add("f");
        arrList.addAll(items);
        return arrList.toArray(new String[0]);
    }
}

class Exercise extends Element {
    private String name;
    private double ccal;

    public Exercise(String name, double ccal) {
        this.name = name;
        this.ccal = ccal;
    }

    @Override
    public String[] toArray() {
        return new String[]{"e", name, String.valueOf(ccal)};
    }
}

class CalorieLimit extends Element {
    private String date;
    private double ccal;

    public CalorieLimit(int year, int month, int day, double ccal) {
        this.date = String.format("%d,%d,%d", year, month, day);
        this.ccal = ccal;
    }

    @Override
    public String[] toArray() {
        return new String[]{date, "c", String.valueOf(ccal)};
    }
}

class ExercisePerformed extends Element {
    private String date, name;
    private double minutes;

    public ExercisePerformed(int year, int month, int day, String name, double minutes) {
        this.date = String.format("%d,%d,%d", year, month, day);
        this.name = name;
        this.minutes = minutes;
    }

    @Override
    public String[] toArray() {
        return new String[]{date, "e", name, String.valueOf(minutes)};
    }
}
