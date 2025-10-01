public class PerceptronClassifier {
    private double[] weights;
    private double learningRate = 1.0;
    private int maxIterations = 1000;

    public PerceptronClassifier(int numFeatures) {
        weights = new double[numFeatures + 1];
    }

    public void train(double[][] data, int[] labels) {
        int iterations = 0;
        boolean allCorrect = false;

        System.out.println("\n=== ПРОЦЕСС ОБУЧЕНИЯ ПЕРСЕПТРОНА ===");

        while (!allCorrect && iterations < maxIterations) {
            allCorrect = true;
            System.out.printf("Итерация %d:\n", iterations + 1);

            for (int i = 0; i < data.length; i++) {
                double[] x = data[i];
                double output = predict(x);

                System.out.printf("  Точка %d (%.3f, %.3f) - метка: %d, выход: %.3f - ",
                        i + 1, x[0], x[1], labels[i], output);

                if (output != labels[i]) {
                    System.out.println("ОШИБКА -> коррекция весов");
                    for (int j = 0; j < x.length; j++) {
                        weights[j + 1] += learningRate * labels[i] * x[j];
                    }
                    weights[0] += learningRate * labels[i];
                    allCorrect = false;
                    printWeights();
                } else {
                    System.out.println("ВЕРНО");
                }
            }
            iterations++;

            if (allCorrect) {
                System.out.println("Все точки классифицированы верно! Обучение завершено.");
            }
        }
    }

    public double predict(double[] x) {
        double sum = weights[0];
        for (int i = 0; i < x.length; i++) {
            sum += weights[i + 1] * x[i];
        }
        return sum >= 0 ? 1 : -1;
    }

    public double[] getWeights() {
        return weights;
    }

    public void printWeights() {
        System.out.printf("    Новые веса: w0=%.3f, w1=%.3f, w2=%.3f\n",
                weights[0], weights[1], weights[2]);
    }

    public String getEquation() {
        return String.format("D = %.3f + %.3f*X1 + %.3f*X2", weights[0], weights[1], weights[2]);
    }
}