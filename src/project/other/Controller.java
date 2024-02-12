package project.other;

import project.other.Models.AbstractModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;


public class Controller {

    private AbstractModel model;
    private final String name;

    private String[] columns;


    public Controller(String modelName) {
        this.name = modelName;
        try {
            model = (AbstractModel) Class.forName(name).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Controller readDataFrom(String fName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fName));
            String line;
            line = reader.readLine();
            this.columns = line.split("\\t");

            Field LL = Class.forName(name).getDeclaredField("LL");

            LL.setAccessible(true);
            LL.set(model, columns.length);

            while ((line = reader.readLine()) != null) {
                double[] row = new double[columns.length];
                String[] parts = line.split("\\t");
                Field rowName = Class.forName(name).getDeclaredField(parts[0]);
                rowName.setAccessible(true);

                int j = 0;
                for (int i = 1; i < row.length + 1; i++) {
                    if (i < parts.length) {
                        row[i - 1] = Double.parseDouble(parts[i]);
                        j++;
                    } else {
                        row[i - 1] = Double.parseDouble(parts[j]);
                    }

                }
                rowName.set(model, row);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public void runModel() {
        model.run();
    }

    public String getResultsAsTsv() {
        StringBuilder result = new StringBuilder();
        try {

            Field[] fields = Class.forName(name).getDeclaredFields();
            for (String str : columns) {
                result.append(str);
                result.append("\t");
            }
            result.append("\n");

            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Bind.class)) {
                    if (field.getType().isArray()) {
                        Object value = field.get(model);

                        for (int i = 0; i < Array.getLength(value); i++) {
                            if (i == 0) {
                                result.append(field.getName());
                                result.append("\t");
                            } else {
                                result.append(Array.get(value, i));
                                result.append("\t");
                            }

                        }
                        result.append("\n");

                    }

                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result.toString();
    }


}
