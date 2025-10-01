public class MinimumDistanceClassifier {
    private double[][] prototypes;
    private String[] classLabels;
    private double[] maxValues;

    public MinimumDistanceClassifier(double[][] data, int[] labels, String[] classLabels, double[] maxValues) {
        this.classLabels = classLabels;
        this.maxValues = maxValues;

        int numClasses = classLabels.length;
        int numFeatures = data[0].length;
        prototypes = new double[numClasses][numFeatures];
        int[] count = new int[numClasses];

        for (int i = 0; i < data.length; i++) {
            int classIndex = labels[i];
            for (int j = 0; j < numFeatures; j++) {
                prototypes[classIndex][j] += data[i][j];
            }
            count[classIndex]++;
        }

        for (int i = 0; i < numClasses; i++) {
            for (int j = 0; j < numFeatures; j++) {
                prototypes[i][j] /= count[i];
            }
        }

        normalizePrototypes();
    }

    private void normalizePrototypes() {
        for (int i = 0; i < prototypes.length; i++) {
            for (int j = 0; j < prototypes[i].length; j++) {
                prototypes[i][j] /= maxValues[j];
            }
        }
    }

    public String classify(double[] point) {
        double[] normalizedPoint = normalizePoint(point);

        double maxD = Double.NEGATIVE_INFINITY;
        int bestClass = -1;

        for (int i = 0; i < prototypes.length; i++) {
            double d = calculateDiscriminant(normalizedPoint, prototypes[i]);
            System.out.printf("D(%s) = %.4f\n", classLabels[i], d);

            if (d > maxD) {
                maxD = d;
                bestClass = i;
            }
        }

        return classLabels[bestClass];
    }

    private double calculateDiscriminant(double[] point, double[] prototype) {
        double sum1 = 0; // 2*(X1*P1 + X2*P2)
        double sum2 = 0; // (P1^2 + P2^2)

        for (int i = 0; i < point.length; i++) {
            sum1 += 2 * point[i] * prototype[i];
            sum2 += prototype[i] * prototype[i];
        }

        return sum1 - sum2;
    }

    private double[] normalizePoint(double[] point) {
        double[] normalized = new double[point.length];
        for (int i = 0; i < point.length; i++) {
            normalized[i] = point[i] / maxValues[i];
        }
        return normalized;
    }

    public void printPrototypes() {
        System.out.println("\n=== ПРОТОТИПЫ КЛАССОВ ===");
        System.out.println("Класс\tАцетилен\tУглеводороды");
        for (int i = 0; i < prototypes.length; i++) {
            System.out.printf("%s\t%.3f\t\t%.3f\n",
                    classLabels[i], prototypes[i][0], prototypes[i][1]);
        }
    }

    public double[][] getPrototypes() {
        return prototypes;
    }
}