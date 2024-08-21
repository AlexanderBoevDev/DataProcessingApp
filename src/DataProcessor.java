import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataProcessor {
    private List<String> data;

    public DataProcessor() {
        data = new ArrayList<>();
    }

    // Метод для добавления данных
    public void addData(String line) {
        data.add(line);
    }

    // Метод для сортировки данных
    public void sortData() {
        Collections.sort(data);
    }

    // Метод для сортировки данных в обратном порядке
    public void sortDataReverse() {
        Collections.sort(data, Collections.reverseOrder());
    }

    // Метод для фильтрации данных по префиксу
    public void filterDataByPrefix(String prefix) {
        final String finalPrefix = prefix.toLowerCase(); // Приведение префикса к нижнему регистру и сохранение в новой переменной
        data.removeIf(line -> !line.toLowerCase().startsWith(finalPrefix)); // Приведение строк к нижнему регистру и фильтрация
    }

    // Метод для анализа данных
    public void analyzeData() {
        int totalLines = data.size();
        String longestLine = data.stream().max((s1, s2) -> Integer.compare(s1.length(), s2.length())).orElse("");

        System.out.println("Анализ данных:");
        System.out.println("Общее количество строк: " + totalLines);
        System.out.println("Самая длинная строка: " + longestLine);
    }

    // Метод для получения данных
    public List<String> getData() {
        return data;
    }
}
