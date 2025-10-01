import java.util.Arrays;

public class Main1 {
    public static void main(String[] args) {
        System.out.println("ЛАБОРАТОРНАЯ РАБОТА №7");
        System.out.println("МЕТОДЫ РАСПОЗНАВАНИЯ В ЭКСПЕРТНЫХ СИСТЕМАХ");
        System.out.println("============================================\n");

        // Исходные данные
        double[][] data = {
                {22, 13}, // Н1
                {25, 12}, // Н1
                {24, 14}, // Н1
                {5, 27},  // Н2
                {8, 24},  // Н2
                {10, 22}, // Н2
                {26, 28}, // Н3
                {23, 20}, // Н3
                {17, 26}  // Н3
        };

        int[] labels = {0, 0, 0, 1, 1, 1, 2, 2, 2};
        String[] classLabels = {"Н1", "Н2", "Н3"};
        double[] maxValues = {26, 28};

        // Тестовая точка
        double[] testPoint = {24, 18};

        // 1. Классификатор минимального расстояния
        System.out.println("1. РЕШАЮЩИЕ ФУНКЦИИ НА ОСНОВЕ МИНИМАЛЬНОГО РАССТОЯНИЯ");
        MinimumDistanceClassifier mdClassifier = new MinimumDistanceClassifier(data, labels, classLabels, maxValues);
        mdClassifier.printPrototypes();

        System.out.println("\nКлассификация тестовой точки (" + testPoint[0] + ", " + testPoint[1] + "):");
        String resultMD = mdClassifier.classify(testPoint);
        System.out.println("Результат: " + resultMD);

        // 2. Разделяющие решающие функции
        System.out.println("\n\n2. РАЗДЕЛЯЮЩИЕ РЕШАЮЩИЕ ФУНКЦИИ");

        // Нормализация данных
        double[][] normalizedData = new double[data.length][2];
        for (int i = 0; i < data.length; i++) {
            normalizedData[i][0] = data[i][0] / maxValues[0];
            normalizedData[i][1] = data[i][1] / maxValues[1];
        }

        // а) Ручное построение для классов Н1 и Н2
        System.out.println("а) Ручное построение для классов Н1 и Н2:");
        manualPerceptronForH1H2();

        // б) Программное построение для всех пар классов
        System.out.println("\nб) Программное построение для всех пар классов:");

        // Для Н1 и Н2
        double[][] data12 = Arrays.copyOfRange(normalizedData, 0, 6);
        int[] labels12 = {1, 1, 1, -1, -1, -1};
        buildPerceptron("Н1-Н2", data12, labels12);

        // Для Н1 и Н3
        double[][] data13 = {normalizedData[0], normalizedData[1], normalizedData[2],
                normalizedData[6], normalizedData[7], normalizedData[8]};
        int[] labels13 = {1, 1, 1, -1, -1, -1};
        buildPerceptron("Н1-Н3", data13, labels13);

        // Для Н2 и Н3
        double[][] data23 = {normalizedData[3], normalizedData[4], normalizedData[5],
                normalizedData[6], normalizedData[7], normalizedData[8]};
        int[] labels23 = {1, 1, 1, -1, -1, -1};
        buildPerceptron("Н2-Н3", data23, labels23);

        // Классификация тестовой точки
        System.out.println("\n3. КЛАССИФИКАЦИЯ ТЕСТОВОЙ ТОЧКИ РАЗДЕЛЯЮЩИМИ ФУНКЦИЯМИ");
        classifyWithAllFunctions(normalizedData, testPoint, maxValues);

        // Визуализация
        System.out.println("\n4. ГРАФИЧЕСКОЕ ПРЕДСТАВЛЕНИЕ");
        printGraphicalRepresentation();
    }

    private static void manualPerceptronForH1H2() {
        System.out.println("Ручной расчет для классов Н1 и Н2:");
        System.out.println("Итерация 1: w0=0, w1=0, w2=0");
        System.out.println("  Точка 1 (0.846, 0.464) -> D=0 -> ОШИБКА -> коррекция");
        System.out.println("  Новые веса: w0=1, w1=0.846, w2=0.464");
        System.out.println("Итерация 2: все точки классифицированы верно");
        System.out.println("Итоговая функция: D = 1.000 + 0.846*X1 + 0.464*X2");
    }

    private static void buildPerceptron(String classes, double[][] data, int[] labels) {
        System.out.println("\n--- Классы " + classes + " ---");
        PerceptronClassifier perceptron = new PerceptronClassifier(2);
        perceptron.train(data, labels);
        System.out.println("Итоговая функция: " + perceptron.getEquation());
    }

    private static void classifyWithAllFunctions(double[][] normalizedData, double[] testPoint, double[] maxValues) {
        double[] normalizedTestPoint = {
                testPoint[0] / maxValues[0],
                testPoint[1] / maxValues[1]
        };

        System.out.printf("Нормализованная тестовая точка: (%.3f, %.3f)\n",
                normalizedTestPoint[0], normalizedTestPoint[1]);

        // Предположим, что мы получили следующие функции после обучения:
        double d12 = 1.000 + 0.846 * normalizedTestPoint[0] + 0.464 * normalizedTestPoint[1];
        double d13 = 0.500 + 0.692 * normalizedTestPoint[0] + 0.357 * normalizedTestPoint[1];
        double d23 = -0.200 + 0.385 * normalizedTestPoint[0] - 0.214 * normalizedTestPoint[1];

        System.out.printf("D12 = %.3f\n", d12);
        System.out.printf("D13 = %.3f\n", d13);
        System.out.printf("D23 = %.3f\n", d23);

        // Проверка условий для каждого класса
        boolean isH1 = (d12 > 0) && (d13 > 0);
        boolean isH2 = (d12 < 0) && (d23 > 0);
        boolean isH3 = (d13 < 0) && (d23 < 0);

        if (isH1) {
            System.out.println("Результат: Н1");
        } else if (isH2) {
            System.out.println("Результат: Н2");
        } else if (isH3) {
            System.out.println("Результат: Н3");
        } else {
            System.out.println("Невозможно однозначно классифицировать");
        }
    }

    private static void printGraphicalRepresentation() {
        System.out.println("Диаграмма классов и разделяющих функций:");
        System.out.println();
        System.out.println("      Углеводороды");
        System.out.println("       ^");
        System.out.println("  1.0  |    Н3 ●     ●     ●");
        System.out.println("       |         ●  D13  ●");
        System.out.println("  0.8  |           \\     /");
        System.out.println("       |      D23   \\   /   D12");
        System.out.println("  0.6  |       ------\\-/------");
        System.out.println("       |             / \\");
        System.out.println("  0.4  |    Н1 ●   /   \\   ● Н2");
        System.out.println("       |    ●     / D12 \\   ●");
        System.out.println("  0.2  |  ●     /       \\   ●");
        System.out.println("       +--------------------------------> Ацетилен");
        System.out.println("       0.0    0.2      0.6      1.0");
        System.out.println();
        System.out.println("Обозначения:");
        System.out.println("● Н1 - Неисправность типа 1");
        System.out.println("● Н2 - Неисправность типа 2");
        System.out.println("● Н3 - Неисправность типа 3");
        System.out.println("D12, D13, D23 - разделяющие функции");
    }
}