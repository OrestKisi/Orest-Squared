import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CSVWriter {

    public void write(Element el) throws IOException {
        String[] data = el.toArray();
        File csvFile = determineFile(el);

        if ((el instanceof Food || el instanceof Exercise) && checkIfExists(csvFile, data[1])) {
            System.out.print(el.getClass().toString().substring(6) + " name already exists. Skipping writing to file.");
            return;
        }

        try (FileWriter fileWriter = new FileWriter(csvFile, true)) {
            String line = String.join(",", data) + "\n";
            fileWriter.write(line);
        }
    }

    private File determineFile(Element el) {
        if (el instanceof Log || el instanceof Consumption || el instanceof CalorieLimit || el instanceof ExercisePerformed) {
            return new File("log.csv");
        } else if (el instanceof Exercise) {
            return new File("exercise.csv");
        } else {
            return new File("food.csv");
        }
    }

    public List<List<String>> reading(int id) throws IOException {
        String fileName = getFileName(id);
        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        }
        return records;
    }

    private String getFileName(int id) {
        if (id == 1 || id == 0) {
            return "food.csv";
        } else if (id == 2) {
            return "exercise.csv";
        } else {
            return "log.csv";
        }
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

    public Element getByName(int id, String name) throws IOException {
        List<List<String>> list = reading(id);

        for (List<String> entry : list) {
            if (entry.size() < 2) continue;

            if (entry.get(1).equals(name)) {
                return parseEntry(id, entry);
            }
        }
        return null;
    }

    private Element parseEntry(int id, List<String> entry) {
        switch (id) {
            case 0:
                return new Food(entry.get(1), Integer.parseInt(entry.get(2)), Integer.parseInt(entry.get(3)),
                        Integer.parseInt(entry.get(4)), Integer.parseInt(entry.get(5)));
            case 1:
                return new Recipe(entry.get(1), entry.subList(1, entry.size()));
            case 2:
                return new Exercise(entry.get(1), Double.parseDouble(entry.get(2)));
            case 3:
                return new Log(Integer.parseInt(entry.get(0)), Integer.parseInt(entry.get(1)),
                        Integer.parseInt(entry.get(2)), Double.parseDouble(entry.get(4)));
            case 4:
                return new Consumption(Integer.parseInt(entry.get(0)), Integer.parseInt(entry.get(1)),
                        Integer.parseInt(entry.get(2)), entry.subList(3, entry.size()));
            case 5:
                return new CalorieLimit(Integer.parseInt(entry.get(0)), Integer.parseInt(entry.get(1)),
                        Integer.parseInt(entry.get(2)), Double.parseDouble(entry.get(4)));
            case 6:
                return new ExercisePerformed(Integer.parseInt(entry.get(0)), Integer.parseInt(entry.get(1)),
                        Integer.parseInt(entry.get(2)), entry.get(4), Double.parseDouble(entry.get(5)));
            default:
                return null;
        }
    }

    public List<ExercisePerformed> getExercisesPerformedByDate(int year, int month, int day) throws IOException {
        List<List<String>> logList = reading(6); // Assuming exercises performed are stored with id 6
        List<ExercisePerformed> foundEntries = new ArrayList<>();

        for (List<String> logEntry : logList) {
            int logYear = Integer.parseInt(logEntry.get(0));
            int logMonth = Integer.parseInt(logEntry.get(1));
            int logDay = Integer.parseInt(logEntry.get(2));

            if (logYear == year && logMonth == month && logDay == day && logEntry.contains("e")) {
                foundEntries.add(parseExercisePerformed(logEntry));
            }
        }
        return foundEntries;
    }

    public Element getLogByDate(int id, int year, int month, int day) throws IOException {
        List<List<String>> logList = reading(3); // Assuming logs are stored with id 3
        Element foundEntry = null;

        for (List<String> logEntry : logList) {
            int logYear = Integer.parseInt(logEntry.get(0));
            int logMonth = Integer.parseInt(logEntry.get(1));
            int logDay = Integer.parseInt(logEntry.get(2));

            if (logYear == year && logMonth == month && logDay == day) {
                if ((id == 3 && logEntry.contains("w")) || (id == 4 && logEntry.contains("f"))
                        || (id == 5 && logEntry.contains("c")) || (id == 6 && logEntry.contains("e"))) {
                    foundEntry = parseEntry(id, logEntry);
                }
            }
        }
        return foundEntry;
    }

    private ExercisePerformed parseExercisePerformed(List<String> entry) {
        return new ExercisePerformed(Integer.parseInt(entry.get(0)), Integer.parseInt(entry.get(1)),
                Integer.parseInt(entry.get(2)), entry.get(4), Double.parseDouble(entry.get(5)));
    }


    public String[] getProductNames() {
        List<List<String>> list;
        try {
            list = reading(1);
        } catch (IOException e) {
            System.out.println("Reading Error");
            return null;
        }
        return extractNames(list, "b");
    }



    public String[] getExerciseNames() {
        List<List<String>> list;
        try {
            list = reading(2);
        } catch (IOException e) {
            System.out.println("Reading Error");
            return null;
        }
        return extractNames(list, "e");
    }

    private String[] extractNames(List<List<String>> list, String type) {
        List<String> names = new ArrayList<>();
        for (List<String> entry : list) {
            if (entry.size() < 2) continue;
            if (entry.get(0).equals(type)) {
                names.add(entry.get(1));
            }
        }
        return names.toArray(new String[0]);
    }
}
