package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     *
     * Время: O(n*Log(n))
     * Память: S(n)
     *
     */

    static public void sortTimes(String inputName, String outputName) throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputName))) {

            ArrayList<String> array = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                array.add(line);
            }

            array.sort(new Comparator<String>() {
                DateFormat f = new SimpleDateFormat("hh:mm:ss a");

                @Override
                public int compare(String o1, String o2) {
                    try {
                        return f.parse(o1).compareTo(f.parse(o2));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });

            for (String string: array) {
                bufferedWriter.write(string);
                bufferedWriter.newLine();
            }

        }

    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     *
     * Время: O(n*Log(n))
     * Память: S(n)
     *
     */
    static public void sortAddresses(String inputName, String outputName) throws IOException {

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), "UTF-8"));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputName), "UTF-8"))) {

            Map<String, String> map = new TreeMap<>(
                    (o1, o2) -> {
                        String[] address1 = o1.split(" ");
                        String[] address2 = o2.split(" ");
                        int result;
                        if (!address1[0].equals(address2[0])) {
                            result = o1.compareTo(o2);
                        } else {
                            result = Integer.valueOf(address1[1]).compareTo(Integer.valueOf(address2[1]));
                        }
                        return result;
                    });

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> array = Arrays.asList(line.split(" - "));
                if (map.get(array.get(1)) != null) {
                    map.put(array.get(1), map.get(array.get(1)) + ", " +  array.get(0));
                    List<String> peopleNames = Arrays.asList(map.get(array.get(1)).split(", "));
                    Collections.sort(peopleNames);
                    String nameListToString = String.join(", ", peopleNames);
                    map.put(array.get(1), nameListToString);
                } else {
                    map.put(array.get(1), array.get(0));
                }
            }

            for (Map.Entry<String, String> pair : map.entrySet()) {
                bufferedWriter.write(pair.getKey());
                bufferedWriter.write(" - ");
                bufferedWriter.write(pair.getValue());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        }

    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     *
     * Время: O(n*Log(n))
     * Память: S(n)
     *
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), "UTF-8"));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputName), "UTF-8"))) {

            String line;
            ArrayList<Double> arrayList = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                arrayList.add(Double.parseDouble(line));
            }

            Collections.sort(arrayList);

            for (Double number: arrayList) {
                bufferedWriter.write(number.toString());
                bufferedWriter.newLine();
            }
        }
    }


    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     *
     * Время: O(n)
     * Память: S(2*n) -> S(n)
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {

        long a = System.currentTimeMillis();

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), "UTF-8"));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputName), "UTF-8"))) {

            String line;
            ArrayList<Integer> originArray = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                originArray.add(Integer.parseInt(line));
            }

            Map<Integer, Integer> countOfNumbers = new HashMap<>();

            for (Integer num : originArray) {
                if (countOfNumbers.containsKey(num)) {
                    countOfNumbers.put(num, countOfNumbers.get(num) + 1);
                } else {
                    countOfNumbers.put(num, 1);
                }
            }

            Integer valueOfMax = Integer.MAX_VALUE;
            Integer countOfMax = 0;

            for (Map.Entry<Integer, Integer> pair : countOfNumbers.entrySet()) {

                if (pair.getValue().equals(countOfMax) &&  pair.getKey() < valueOfMax) {
                    countOfMax = pair.getValue();
                    valueOfMax = pair.getKey();
                }

                if (pair.getValue() > countOfMax) {
                    countOfMax = pair.getValue();
                    valueOfMax = pair.getKey();
                }
            }

            for (Integer number : originArray) {
                if (!number.equals(valueOfMax)) {
                    bufferedWriter.write(number.toString());
                    bufferedWriter.newLine();
                } else {
                    continue;
                }
            }

            while (countOfMax > 0) {
                bufferedWriter.write(valueOfMax.toString());
                bufferedWriter.newLine();
                countOfMax--;
            }

            System.out.println(System.currentTimeMillis() - a);

        }
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
