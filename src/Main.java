import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static final String CLASS_1 = "Iris-virginica";
    public static final String CLASS_2 = "Iris-versicolor";

    public static void main(String[] args) {
        String trainPath = "Data/4v/train_set_v4.csv";
        String testPath = "Data/4v/test_set_v4.csv";
        Perceptron perceptron = null;

        boolean isRunning = true;
        int choice;

        while (isRunning) {
            System.out.println("----------------------------MENU------------------------");
            System.out.println("| 1-> Wyswietl dane                                    |");
            System.out.println("| 2-> Uczenie perceptronu                              |");
            System.out.println("| 3-> Testowanie perceptronu        (wymagane uczenie) |");
            System.out.println("| 4-> Klasyfikacja wektora          (wymagane uczenie) |");
            System.out.println("| 5-> Wyjście                                          |");
            System.out.println("--------------------------------------------------------");

            System.out.println("Wybierz opcję: ");
            Scanner input = new Scanner(System.in);
            choice = input.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.println("Wyswietl dane");
                    System.out.println("1. Dane treningowe");
                    System.out.println("2. Dane testowe");
                    System.out.println("Wybierz opcję: ");
                    int choice1 = input.nextInt();
                    switch (choice1) {
                        case 1 -> {
                            System.out.println("Dane treningowe");
                            displayData(trainPath);
                        }
                        case 2 -> {
                            System.out.println("Dane testowe");
                            displayData(testPath);
                        }
                    }
                }
                case 2 -> {
                    System.out.println("Trening perceptronu");
                    System.out.println("1. Użyj danych treningowych z pliku wgranego do programu");
                    System.out.println("2. Użyj danych treningowych z pliku zewnętrznego");
                    System.out.println("Wybierz opcję: ");
                    int choice2 = input.nextInt();
                    switch (choice2) {
                        case 1 -> {
                            System.out.println("Użyj danych treningowych z pliku wgranego do programu");
                            System.out.println("Podaj współczynnik uczenia: ");
                            double learningRate = input.nextDouble();
                            List<Irys> train = File.readIrysData(trainPath, 4, CLASS_1, CLASS_2);

                            perceptron = new Perceptron(4, learningRate);

                            boolean accepted;
                            do {
                                perceptron.train(train);
                                System.out.println("Perceptron został przetrenowany");
                                double accuracy = calculatePerceptronAccuracy(testPath, perceptron);
                                accuracy = Math.round(accuracy * 100.0) / 100.0;

                                System.out.println("Liczba epok: " + perceptron.GetEpoch());
                                System.out.println("Czy akceptujesz dokładność " + accuracy + "%? (tak/nie)");
                                String response = input.next();
                                accepted = response.equalsIgnoreCase("tak");

                                if (!accepted) {
                                    System.out.println("Przetrenowanie perceptronu ponownie...");
                                }
                            } while (!accepted);
                        }
                        case 2 -> {
                            System.out.println("Użyj danych treningowych z pliku zewnętrznego");
                            System.out.println("Podaj ścieżkę do pliku train: ");
                            String pathTrain = input.next();
                            System.out.println("Podaj ścieżkę do pliku test: ");
                            String pathTest = input.next();
                            System.out.println("Podaj współczynnik uczenia: ");
                            double learningRate = input.nextDouble();
                            System.out.println("Podaj klasę 1: ");
                            String class1 = input.next();
                            System.out.println("Podaj klasę 2: ");
                            String class2 = input.next();
                            System.out.println("Podaj liczbę cech: ");
                            int features = input.nextInt();
                            List<Irys> trainFromUser = File.readIrysData(pathTrain, features, class1, class2);
                            perceptron = new Perceptron(features, learningRate);

                            boolean accepted;
                            do {
                                perceptron.train(trainFromUser);
                                System.out.println("Perceptron został przetrenowany.");
                                double accuracy = calculatePerceptronAccuracy(pathTest, perceptron);
                                accuracy = Math.round(accuracy * 100.0) / 100.0;


                                System.out.println("Czy akceptujesz dokładność " + accuracy + "%? (tak/nie)");
                                System.out.println("Liczba epok: " + perceptron.GetEpoch());
                                String response = input.next();
                                accepted = response.equalsIgnoreCase("tak");

                                if (!accepted) {
                                    System.out.println("Przetrenowanie perceptronu ponownie...");
                                }
                            } while (!accepted);
                        }
                    }
                }
                case 3 -> {
                    System.out.println("Testowanie perceptronu");
                    if (perceptron == null) {
                        System.out.println("Najpierw przeprowadź trening perceptronu.");
                    } else {
                        System.out.println("1. Użyj danych testowych z pliku wgranego do programu");
                        System.out.println("2. Użyj danych testowych z pliku zewnętrznego");
                        System.out.println("Wybierz opcję: ");
                        int choice3 = input.nextInt();
                        switch (choice3) {
                            case 1 -> {
                                System.out.println("Testowanie perceptronu");
                                List<Irys> testData = File.readIrysData(testPath, 4, CLASS_1, CLASS_2);
                                Map<String, Double> testResults = perceptron.testPerceptron(testData);

                                displayAccuracyResults(testResults);
                            }
                            case 2 -> {
                                System.out.println("Użyj danych testowych z pliku zewnętrznego");
                                System.out.println("Podaj ścieżkę do pliku: ");
                                String path = input.next();
                                System.out.println("Podaj klasę 1: ");
                                String class1 = input.next();
                                System.out.println("Podaj klasę 2: ");
                                String class2 = input.next();
                                System.out.println("Podaj liczbę cech: ");
                                int features = input.nextInt();
                                List<Irys> testDataFromUser = File.readIrysData(path, features, class1, class2);
                                Map<String, Double> testResults = perceptron.testPerceptron(testDataFromUser);

                                displayAccuracyResults(testResults);
                            }
                        }
                    }
                }
                case 4 -> {
                    System.out.println("Klasyfikuj pojedynczy wektor");
                    if (perceptron == null) {
                        System.out.println("Najpierw przeprowadź trening perceptronu.");
                    } else {
                        System.out.println("Podaj ilość cech: ");
                        int features = input.nextInt();
                        classifySingleVector(input, perceptron, features);
                    }
                }
                case 5 -> {
                    System.out.println("Zakończ");
                    isRunning = false;
                }
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    private static void displayData(String filePath) {
        System.out.println("Class 1: " + Main.CLASS_1);
        System.out.println("Class 2: " + Main.CLASS_2);
        List<Irys> data = File.readIrysData(filePath, 4, Main.CLASS_1, Main.CLASS_2);
        File.showData(data);
    }

    private static void displayAccuracyResults(Map<String, Double> testResults) {
        double totalAccuracy = 0;
        int totalClasses = 0;
        for (Map.Entry<String, Double> entry : testResults.entrySet()) {
            String className = entry.getKey();
            double classAccuracy = entry.getValue();
            double roundedClassAccuracy = Math.round(classAccuracy * 100 * 100) / 100.0;
            System.out.println("Dokładność dla gatunku " + className + ": " + roundedClassAccuracy + "%");
            totalAccuracy += classAccuracy;
            totalClasses++;
        }
        double roundedTotalAccuracy = Math.round((totalAccuracy / totalClasses) * 100 * 100) / 100.0;
        System.out.println("Łączna dokładność: " + roundedTotalAccuracy + "%");
    }


    private static double calculatePerceptronAccuracy(String testPath, Perceptron perceptron) {
        List<Irys> testData = File.readIrysData(testPath, 4, CLASS_1, CLASS_2);
        int correctPredictions = 0;
        int totalPredictions = testData.size();
        for (Irys sample : testData) {
            double[] inputs =
                    sample.getFeatures();
            int label = sample.getClassName().equals(CLASS_1) ? 1 : 0;
            int prediction = perceptron.predict(inputs);
            if (prediction == label) {
                correctPredictions++;
            }
        }
        return (double) correctPredictions / totalPredictions * 100;
    }

    private static void classifySingleVector(Scanner input, Perceptron perceptron, int features) {
        System.out.println("Podaj wartości cech pojedynczego wektora:");
        double[] userVector = new double[features];

        for (int i = 0; i < userVector.length; i++) {
            System.out.print("Cecha " + (i + 1) + ": ");
            userVector[i] = input.nextDouble();
        }

        int prediction = perceptron.predict(userVector);
        String predictedClass = prediction == 1 ? CLASS_1 : CLASS_2;
        System.out.println("Przewidywana klasa dla podanego wektora: " + predictedClass);
    }
}
