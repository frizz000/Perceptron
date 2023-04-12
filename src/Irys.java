public class Irys {
    private final double[] features;
    private final String className;

    public Irys(double[] features, String className) {
        this.features = features;
        this.className = className;
    }

    public double[] getFeatures() {
        return features;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double feature : features) {
            sb.append(feature).append(", ");
        }
        sb.append(className);
        return sb.toString();
    }
}
