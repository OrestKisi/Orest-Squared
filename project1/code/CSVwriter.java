import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.io.*;
import java.util.Arrays;
import java.util.List;

class CSVWriter {

    public void write(Element el) throws IOException {
        String[] data = el.toArray();
        File csvFile;

        if (el instanceof Log || el instanceof Consumption || el instanceof CalorieLimit || el instanceof ExercisePerformed) {
            csvFile = new File("log.csv");
        } else if (el instanceof Exercise) {
            csvFile = new File("exercise.csv");
        } else {
            csvFile = new File("food.csv");
        }

        // Check if the food name already exists
        if (el instanceof Food && checkIfExists(csvFile, data[1])) {
            System.out.println("Food name already exists. Skipping writing to file.");
            return;
        }

        FileWriter fileWriter = new FileWriter(csvFile, true);

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            line.append(data[i].replaceAll("\"", "\"\""));
            if (i != data.length - 1) {
                line.append(',');
            }
        }
        line.append("\n");
        fileWriter.write(line.toString());

        fileWriter.close();
    }

    public List<List<String>> reading(int id) throws FileNotFoundException, IOException {
        String name = "";
        if (id == 1 || id == 0) {
            name = "food.csv";
        } else if (id == 2) {
            name = "exercise.csv";
        } else {
            name = "log.csv";
        }

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(name))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        }
        return records;
    }

    private boolean checkIfExists(File csvFile, String foodName) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(foodName)) {
                    return true; // Food name already exists in the file
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Food name does not exist in the file
    }

    public Element getByName(int id, String name) throws FileNotFoundException, IOException {
        Element element = null;
        List<List<String>> list = reading(id);
    
        for (List<String> entry : list) {
            // Ensure the entry has enough elements to parse
            if (entry.size() >= 5) { // Check if the entry has at least 6 elements

                // Check if the entry matches the specified name
                if (entry.get(1).equals(name)) {
                    if (id == 0 && entry.contains("b")) {
                        // Parse Food entry
                        element = new Food(name, Integer.parseInt(entry.get(2)), Integer.parseInt(entry.get(3)),
                                Integer.parseInt(entry.get(4)), Integer.parseInt(entry.get(5)));
                    } else if (id == 1 && entry.contains("r")) {
                        // Parse Recipe entry
                        ArrayList<String> al = new ArrayList<>(entry.subList(1, entry.size()));
                        element = new Recipe(name, al);
                    } 
                }
            }else {
                if (id == 2 && entry.contains("e") && entry.get(1).equals(name)) {
                    // Parse Exercise entry
                    element = new Exercise(name, Double.parseDouble(entry.get(2)));
                }
            } 
        }
        return element;
    }
    

    public Element getLogByDate(int id, int year, int month, int day) throws IOException {
        List<List<String>> logList = reading(3); // Assuming logs are stored with id 3
        Element foundEntry = null;

        // Iterate over the log list
        for (List<String> logEntry : logList) {
            // Assuming the format of log entry is "year,month,day,..."
            int logYear = Integer.parseInt(logEntry.get(0));
            int logMonth = Integer.parseInt(logEntry.get(1));
            int logDay = Integer.parseInt(logEntry.get(2));

            // Check if the log entry matches the specified date
            if (logYear == year && logMonth == month && logDay == day) {
                // Check if the entry matches the specified type and update the found entry
                if ((id == 3 && logEntry.contains("w")) || (id == 4 && logEntry.contains("f")) || (id == 5 && logEntry.contains("c") || (id == 6 && logEntry.contains("e"))) ) {
                    foundEntry = parseEntry(id, logEntry); // Parse the entry based on its type
                }
            }
        }
        return foundEntry; // Return the last found entry
    }

    // Parse the log or consumption entry based on its type
    private Element parseEntry(int id, List<String> entry) {
        switch (id) {
            case 3:
                // Parse Log entry
                double weight = Double.parseDouble(entry.get(4)); 
                return new Log(Integer.parseInt(entry.get(0)), Integer.parseInt(entry.get(1)),
                        Integer.parseInt(entry.get(2)), weight);
            case 4:
                // Parse Consumption entry
                ArrayList<String> details = new ArrayList<>(entry.subList(3, entry.size()));
                return new Consumption(Integer.parseInt(entry.get(0)), Integer.parseInt(entry.get(1)),
                        Integer.parseInt(entry.get(2)), details);
            case 5:
                // Parse CalLimit entry
                double ccal = Double.parseDouble(entry.get(4)); 
                return new CalorieLimit(Integer.parseInt(entry.get(0)), Integer.parseInt(entry.get(1)),
                        Integer.parseInt(entry.get(2)), ccal);

            case 6:
                // Parse ExercicePerformed entry
                String name = entry.get(4);
                double mins = Double.parseDouble(entry.get(5));
                return new ExercisePerformed(Integer.parseInt(entry.get(0)), Integer.parseInt(entry.get(1)),
                        Integer.parseInt(entry.get(2)), name, mins);
            default:
                return null; // Unknown entry type
        }
    }


    public List<ExercisePerformed> getExercisesPerformedByDate(int year, int month, int day) throws IOException {
        List<List<String>> logList = reading(6); // Assuming exercises performed are stored with id 6
        List<ExercisePerformed> foundEntries = new ArrayList<>();
    
        // Iterate over the log list
        for (List<String> logEntry : logList) {
            // Assuming the format of log entry is "year,month,day,..."
            int logYear = Integer.parseInt(logEntry.get(0));
            int logMonth = Integer.parseInt(logEntry.get(1));
            int logDay = Integer.parseInt(logEntry.get(2));
    
            // Check if the log entry matches the specified date
            if (logYear == year && logMonth == month && logDay == day && logEntry.contains("e")) {
                // Parse the exercise performed entry and add it to the list of found entries
                foundEntries.add(parseExercisePerformed(logEntry));
            }
        }
        return foundEntries;
    }
    
    // Parse the ExercisePerformed entry
    private ExercisePerformed parseExercisePerformed(List<String> entry) {
        String name = entry.get(4);
        double mins = Double.parseDouble(entry.get(5));
        return new ExercisePerformed(Integer.parseInt(entry.get(0)), Integer.parseInt(entry.get(1)),
                Integer.parseInt(entry.get(2)), name, mins);
    }


    
    public String[] getProductNames() {
        List<List<String>> list = null;

        try {
            list = reading(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] arr = null;
        ArrayList<String> al = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            List<String> inList = list.get(i);

            for (int j = 0; j < inList.size(); j++) {
                if (inList.get(j).equals("b")) {
                    al.add(inList.get(1));
                }
            }
        }

        arr = new String[al.size()];
        for (int i = 0; i < al.size(); i++) {
            arr[i] = al.get(i);
        }
        return arr;
    }

    public String[] getExerciseNames() {
        List<List<String>> list = null;

        try {
            list = reading(2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] arr = null;
        ArrayList<String> al = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            List<String> inList = list.get(i);

            for (int j = 0; j < inList.size(); j++) {
                if (inList.get(j).equals("e")) {
                    al.add(inList.get(1));
                }
            }
        }

        arr = new String[al.size()];
        for (int i = 0; i < al.size(); i++) {
            arr[i] = al.get(i);
        }
        return arr;
    }
}

/**
 *  
 */
class Element {

    public String[] toArray() {
        String[] arr = null;
        return arr;
    }
}

class Food extends Element {

    private String name;
    private int ccal, fat, carbohydrates, protein;

    public Food(String _name, int _ccal, int _fat, int _carbohydrates, int _protein) {
        name = _name;
        ccal = _ccal;
        fat = _fat;
        carbohydrates = _carbohydrates;
        protein = _protein;
    }

    public String[] toArray() {
        String[] arr = { "b", name, String.valueOf(ccal), String.valueOf(fat), String.valueOf(protein),
                String.valueOf(carbohydrates) };
        return arr;
    }

}

class Recipe extends Element {

    private String name;
    private ArrayList na;

    public Recipe(String _name, ArrayList names_amounts) {
        name = _name;
        na = names_amounts;
    }

    public String[] toArray() {

        String[] arr = null;
        ArrayList arli = new ArrayList<>();

        arli.add("r");
        arli.add(name);

        for (int i = 0; i < na.size(); i++) {
            arli.add(na.get(i));
        }

        arr = new String[arli.size()];

        for (int i = 0; i < arli.size(); i++) {
            arr[i] = String.valueOf(arli.get(i));
        }

        return arr;
    }

}

class Log extends Element {

    private String date;
    private double weight;

    public Log(int year, int month, int day, double _weight) {
        date = String.valueOf(year) + "," + String.valueOf(month) + "," + String.valueOf(day);
        weight = _weight;
    }

    public String[] toArray() {
        String[] arr = { date, "w", String.valueOf(weight) };
        return arr;
    }
}

class Consumption extends Element {

    private String date;
    private ArrayList na;

    public Consumption(int year, int month, int day, ArrayList names_amounts) {
        date = String.valueOf(year) + "," + String.valueOf(month) + "," + String.valueOf(day);
        na = names_amounts;
    }

    public String[] toArray() {

        String[] arr = null;
        ArrayList arli = new ArrayList<>();

        arli.add(date);
        arli.add("f");

        for (int i = 0; i < na.size(); i++) {
            arli.add(na.get(i));
        }

        arr = new String[arli.size()];

        for (int i = 0; i < arli.size(); i++) {
            arr[i] = String.valueOf(arli.get(i));
        }
        return arr;
    }
}

class Exercise extends Element {

    private String name;
    private double ccal;

    public Exercise(String _name, double _ccal) {
        name = _name;
        ccal = _ccal;
    }

    public String[] toArray() {
        String[] arr = { "e", name, String.valueOf(ccal) };
        return arr;
    }
}

class CalorieLimit extends Element {

    private String date;
    private double ccal;

    public CalorieLimit(int year, int month, int day, double _ccal) {
        date = String.valueOf(year) + "," + String.valueOf(month) + "," + String.valueOf(day);
        ccal = _ccal;
    }

    public String[] toArray() {
        String[] arr = { date, "c", String.valueOf(ccal) };
        return arr;
    }
}

class ExercisePerformed extends Element {

    private String date, name;
    private double min;

    public ExercisePerformed(int year, int month, int day, String _name, double _min) {
        date = String.valueOf(year) + "," + String.valueOf(month) + "," + String.valueOf(day);
        min = _min;
        name = _name;
    }

    public String[] toArray() {
        String[] arr = { date, "e", name, String.valueOf(min) };
        return arr;
    }
}