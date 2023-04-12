import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;


public class Perceptron {
    private final double[] weights;
    private final double learningRate;
    private final Random random;

    public static int epoch = 0;

    public Perceptron(int numOfFeatures, double learningRate) {
        this.weights = new double[numOfFeatures + 1];
        this.learningRate = learningRate;
        this.random = new Random();
        initializeWeights();
    }

    public int GetEpoch() {
    	return epoch;
    }

    private void initializeWeights() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = random.nextDouble() * 2 - 1;
        }
    }

    public int predict(double[] inputs) {
        double thershHold = weights[0];
        for (int i = 0; i < inputs.length; i++) {
            thershHold += weights[i + 1] * inputs[i];
        }
        return thershHold >= 0 ? 1 : 0;
    }

    private double calculateAccuracy(List<Irys> irysSamples) {
        int correctPredictions = 0;
        for (Irys irys : irysSamples) {
            double[] inputs = irys.getFeatures();
            int label = irys.getClassName().equals(Main.CLASS_1) ? 1 : 0;
            int prediction = predict(inputs);
            if (prediction == label) {
                correctPredictions++;
            }
        }
        return (double) correctPredictions / irysSamples.size();
    }

    public void train(List<Irys> irysSamples) {
        double previousAccuracy = 0;
        boolean training = true;
        double currentAccuracy;

        while (training) {
            for (Irys irysSample : irysSamples) {
                double[] inputs = irysSample.getFeatures();
                int label = irysSample.getClassName().equals(Main.CLASS_1) ? 1 : 0;
                int prediction = predict(inputs);
                int error = label - prediction;

                for (int j = 0; j < inputs.length; j++) {
                    weights[j + 1] += learningRate * error * inputs[j];
                }
                weights[0] += learningRate * error;
            }

            epoch++;
            currentAccuracy = calculateAccuracy(irysSamples);

            if (currentAccuracy <= previousAccuracy) {
                training = false;
            } else {
                previousAccuracy = currentAccuracy;
            }
        }
    }

    public Map<String, Double> testPerceptron(List<Irys> testData) {
        Map<String, Integer> correctPredictionsPerClass = new HashMap<>();
        Map<String, Integer> totalSamplesPerClass = new HashMap<>();

        for (Irys irys : testData) {
            String className = irys.getClassName();
            totalSamplesPerClass.put(className, totalSamplesPerClass.getOrDefault(className, 0) + 1);

            double[] inputs = irys.getFeatures();
            int label = className.equals(Main.CLASS_1) ? 1 : 0;
            int prediction = predict(inputs);

            if (prediction == label) {
                correctPredictionsPerClass.put(className, correctPredictionsPerClass.getOrDefault(className, 0) + 1);
            }
        }

        Map<String, Double> accuracyPerClass = new HashMap<>();
        for (String className : totalSamplesPerClass.keySet()) {
            int correctPredictions = correctPredictionsPerClass.getOrDefault(className, 0);
            int totalSamples = totalSamplesPerClass.getOrDefault(className, 0);
            accuracyPerClass.put(className, (double) correctPredictions / totalSamples);
        }
        return accuracyPerClass;
    }
}