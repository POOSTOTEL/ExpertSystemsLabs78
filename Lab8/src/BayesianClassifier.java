public class BayesianClassifier {
    // Априорные вероятности
    private double pImprovement;    // P(H1) - улучшение
    private double pNoImprovement;  // P(H2) - отсутствие улучшения

    // Условные вероятности для симптомов
    private double pTempHigh_H1, pTempHigh_H2;
    private double pPainStrong_H1, pPainStrong_H2;
    private double pBloodNormal_H1, pBloodNormal_H2;

    public BayesianClassifier(double totalPatients, double improvedPatients) {
        this.pImprovement = improvedPatients / totalPatients;
        this.pNoImprovement = (totalPatients - improvedPatients) / totalPatients;
    }

    public void setTemperatureProbabilities(double improvedWithTemp, double totalImproved,
                                            double notImprovedWithTemp, double totalNotImproved) {
        this.pTempHigh_H1 = improvedWithTemp / totalImproved;
        this.pTempHigh_H2 = notImprovedWithTemp / totalNotImproved;
    }

    public void setPainProbabilities(double improvedWithPain, double totalImproved,
                                     double notImprovedWithPain, double totalNotImproved) {
        this.pPainStrong_H1 = improvedWithPain / totalImproved;
        this.pPainStrong_H2 = notImprovedWithPain / totalNotImproved;
    }

    public void setBloodProbabilities(double improvedWithBlood, double totalImproved,
                                      double notImprovedWithBlood, double totalNotImproved) {
        this.pBloodNormal_H1 = improvedWithBlood / totalImproved;
        this.pBloodNormal_H2 = notImprovedWithBlood / totalNotImproved;
    }

    public double calculateProbability() {
        System.out.println("\n=== РАСЧЕТ ВЕРОЯТНОСТИ ПО ТЕОРЕМЕ БАЙЕСА ===");

        // Априорные вероятности
        System.out.printf("Априорные вероятности:\n");
        System.out.printf("P(Улучшение) = %.4f\n", pImprovement);
        System.out.printf("P(Нет улучшения) = %.4f\n", pNoImprovement);

        // Условные вероятности симптомов
        System.out.printf("\nУсловные вероятности симптомов при улучшении:\n");
        System.out.printf("P(Температура 38-40|Улучшение) = %.4f\n", pTempHigh_H1);
        System.out.printf("P(Сильная боль|Улучшение) = %.4f\n", pPainStrong_H1);
        System.out.printf("P(Нет отклонений в крови|Улучшение) = %.4f\n", pBloodNormal_H1);

        System.out.printf("\nУсловные вероятности симптомов без улучшения:\n");
        System.out.printf("P(Температура 38-40|Нет улучшения) = %.4f\n", pTempHigh_H2);
        System.out.printf("P(Сильная боль|Нет улучшения) = %.4f\n", pPainStrong_H2);
        System.out.printf("P(Нет отклонений в крови|Нет улучшения) = %.4f\n", pBloodNormal_H2);

        // P(E|H1) - вероятность симптомов при улучшении
        double pEvidence_H1 = pTempHigh_H1 * pPainStrong_H1 * pBloodNormal_H1;
        System.out.printf("\nP(Симптомы|Улучшение) = %.4f * %.4f * %.4f = %.6f\n",
                pTempHigh_H1, pPainStrong_H1, pBloodNormal_H1, pEvidence_H1);

        // P(E|H2) - вероятность симптомов без улучшения
        double pEvidence_H2 = pTempHigh_H2 * pPainStrong_H2 * pBloodNormal_H2;
        System.out.printf("P(Симптомы|Нет улучшения) = %.4f * %.4f * %.4f = %.6f\n",
                pTempHigh_H2, pPainStrong_H2, pBloodNormal_H2, pEvidence_H2);

        // Числитель и знаменатель формулы Байеса
        double numerator = pImprovement * pEvidence_H1;
        double denominator = numerator + pNoImprovement * pEvidence_H2;

        System.out.printf("\nЧислитель: P(Улучшение) * P(Симптомы|Улучшение) = %.4f * %.6f = %.6f\n",
                pImprovement, pEvidence_H1, numerator);
        System.out.printf("Знаменатель: %.6f + %.4f * %.6f = %.6f\n",
                numerator, pNoImprovement, pEvidence_H2, denominator);

        double result = numerator / denominator;

        System.out.printf("\nИтоговая вероятность: %.6f / %.6f = %.4f\n",
                numerator, denominator, result);

        return result;
    }
}