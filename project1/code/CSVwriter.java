import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.io.*;
import java.util.Arrays;
import java.util.List;


class CSVWriter {

    public void write(Element el) throws IOException{

        String[] data = el.toArray();
        File csvFile = null;

        if(el instanceof Log || el instanceof Consumption){
            csvFile = new File("log.csv");
        }
        else {
            csvFile = new File("food.csv");
        }
        
        FileWriter fileWriter = new FileWriter(csvFile, true);


            StringBuilder line = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                line.append(data[i].replaceAll("\"","\"\""));
                if (i != data.length - 1) {
                    line.append(',');
                }
            }
            line.append("\n");
            fileWriter.write(line.toString());
        
        fileWriter.close();

    }

    public List<List<String>> reading(int id) throws FileNotFoundException, IOException{

        String name = "";
        if(id == 1){
            name = "food.csv";
        }
        else {
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

    public Element getByName(int id, String name) throws FileNotFoundException, IOException{

        Element food = null;
        List<List<String>> list = null;

        //reading 1 = food.csv
        //else = log.csv

        if(id == 1 || id == 0){
            list = reading(1);
        }
        else {
            list = reading(0);
        }
        
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).contains(name)){
                
                List<String> inList = list.get(i);
                
                if(id == 0 && inList.contains("b")){
                    food = new Food(name, Integer.parseInt(inList.get(2)), Integer.parseInt(inList.get(3)), Integer.parseInt(inList.get(4)), Integer.parseInt(inList.get(5)));
                }
                else if(id == 1 && inList.contains("r")){
                    ArrayList al = new ArrayList<>();
                    for (int j = 1; j < inList.size(); j++) {
                        al.add(inList.get(j));
                    }
                    food = new Recipe(name, al);
                }
                else if(id == 2 && inList.contains("w") && inList.get(0).contains(name)){
                    food = new Log(Integer.parseInt(inList.get(0)), Integer.parseInt(inList.get(1)), Integer.parseInt(inList.get(2)), Double.parseDouble(inList.get(4)));
                }
                else if(id == 3 && inList.contains("f")){
                    ArrayList al = new ArrayList<>();
                    for (int j = 3; j < inList.size(); j++) {
                        al.add(inList.get(j));
                    }
                    food = new Consumption(Integer.parseInt(inList.get(0)), Integer.parseInt(inList.get(1)), Integer.parseInt(inList.get(2)), al);
                }
                
            }
        }
        return food;
    }

    public String[] getProductNames(){

        List<List<String>> list = null;

        try {
            list = reading(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] arr = null;
        ArrayList al = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            List<String> inList = list.get(i);

            for (int j = 0; j < inList.size(); j++) {
                if(inList.contains("b")){
                    al.add(inList.get(1));
                }
            }
        }

        arr = new String[al.size()];
        for (int i = 0; i < al.size(); i++) {
            arr[i] = (String)al.get(i);
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

class Food  extends Element{

    private String name;
    private int ccal, fat, carbohydrates, protein;

    public Food(String _name, int _ccal, int _fat, int _carbohydrates, int _protein){
        name = _name; ccal = _ccal; fat = _fat; carbohydrates = _carbohydrates; protein = _protein;
    }

    public String[] toArray() {
        String[] arr = {"b", name, String.valueOf(ccal), String.valueOf(fat), String.valueOf(protein), String.valueOf(carbohydrates)};
        return arr;
    }
    
}

class Recipe extends Element{

    private String name;
    private ArrayList na;

    public Recipe(String _name, ArrayList names_amounts){
        name = _name;  na = names_amounts;
    }

    public String[] toArray() {

        String[] arr = null;
        ArrayList arli = new ArrayList<>();


        arli.add("r"); arli.add(name);

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

class Log extends Element{

    private String date;
    private double weight;

    public Log(int year, int month, int day, double _weight){
        date = String.valueOf(year) + "," + String.valueOf(month) + "," + String.valueOf(day);
        weight = _weight;
    }

    public String[] toArray() {
        String[] arr = {date, "w", String.valueOf(weight)};
        return arr;
    }
}

class Consumption extends Element{

    private String date;
    private ArrayList na;

    public Consumption(int year, int month, int day, ArrayList names_amounts){
        date = String.valueOf(year) + "," + String.valueOf(month) + "," + String.valueOf(day);
        na = names_amounts;
    }


    public String[] toArray() {
        
        String[] arr = null;
        ArrayList arli = new ArrayList<>();

        arli.add(date); arli.add("f");

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