public class Main2 {
    public static void main(String[] args) {
        System.out.println("ЛАБОРАТОРНАЯ РАБОТА №8");
        System.out.println("БАЙЕСОВСКАЯ СТРАТЕГИЯ ОЦЕНКИ ВЫВОДОВ");
        System.out.println("=====================================\n");

        // Исходные данные
        int totalPatients = 300;
        int improvedPatients = 193;
        int notImprovedPatients = totalPatients - improvedPatients;

        System.out.println("ИСХОДНЫЕ ДАННЫЕ:");
        System.out.println("Всего пациентов: " + totalPatients);
        System.out.println("С улучшением: " + improvedPatients);
        System.out.println("Без улучшения: " + notImprovedPatients);

        System.out.println("\nСИМПТОМЫ ПАЦИЕНТА:");
        System.out.println("- Температура: 38.5°C (в диапазоне 38-40)");
        System.out.println("- Боль: сильная");
        System.out.println("- Отклонения в составе крови: нет");

        // Создание и настройка классификатора
        BayesianClassifier classifier = new BayesianClassifier(totalPatients, improvedPatients);

        // Установка вероятностей для температуры 38-40
        int improvedWithTemp = 112;   // улучшение при температуре 38-40
        int notImprovedWithTemp = 23; // нет улучшения при температуре 38-40
        classifier.setTemperatureProbabilities(improvedWithTemp, improvedPatients,
                notImprovedWithTemp, notImprovedPatients);

        // Установка вероятностей для сильной боли
        int improvedWithPain = 109;   // улучшение при сильной боли
        int notImprovedWithPain = 25; // нет улучшения при сильной боли
        classifier.setPainProbabilities(improvedWithPain, improvedPatients,
                notImprovedWithPain, notImprovedPatients);

        // Установка вероятностей для отсутствия отклонений в крови
        int improvedWithBlood = 130;  // улучшение при нормальной крови
        int notImprovedWithBlood = 60; // нет улучшения при нормальной крови
        classifier.setBloodProbabilities(improvedWithBlood, improvedPatients,
                notImprovedWithBlood, notImprovedPatients);

        // Расчет вероятности
        double probability = classifier.calculateProbability();

        // Вывод результата
        System.out.println("\n=== РЕЗУЛЬТАТ ===");
        System.out.printf("Вероятность улучшения при данных симптомах: %.2f%%\n", probability * 100);

        // Интерпретация результата
        System.out.println("\nИНТЕРПРЕТАЦИЯ РЕЗУЛЬТАТА:");
        if (probability > 0.7) {
            System.out.println("Высокая вероятность улучшения - рекомендуется применение лекарства");
        } else if (probability > 0.5) {
            System.out.println("Умеренная вероятность улучшения -可以考虑 применение лекарства");
        } else {
            System.out.println("Низкая вероятность улучшения - рекомендуется рассмотреть альтернативные методы лечения");
        }
    }
}