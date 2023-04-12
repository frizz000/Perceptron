import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class File {

    public static void showData(List<Irys> irysList) {
        for (Irys irys : irysList) {
            System.out.println(irys);
        }
    }

    public static List<Irys> readIrysData(String path, int numOfFeatures, String class1, String class2) {

        List<Irys> irysList = new ArrayList<>();

        try {
            java.io.File file = new java.io.File(path);
            Scanner input = new Scanner(file);

            while (input.hasNext()) {
                String line = input.nextLine();
                String[] values = line.split(",");

                if (!values[values.length - 1].equals(class1) && !values[values.length - 1].equals(class2)) {
                    continue;
                }

                double[] features = new double[numOfFeatures];
                for (int i = 0; i < numOfFeatures; i++) {
                    features[i] = Double.parseDouble(values[i]);
                }

                String className = values[numOfFeatures].equals(class1) ? class1 : class2;
                Irys irys = new Irys(features, className);
                irysList.add(irys);
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return irysList;
    }
}
