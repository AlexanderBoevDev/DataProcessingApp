import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String dataDirectory = "../data";

        boolean continueRunning = true;
        while (continueRunning) {
            System.out.println("Выберите действие:");
            System.out.println("1 - Фильтрация");
            System.out.println("2 - Сортировка");
            System.out.println("3 - Добавить данные");
            System.out.println("4 - Просмотреть содержимое файлов");
            System.out.println("5 - Создать новый файл");
            System.out.println("6 - Удалить файл");
            System.out.println("7 - Завершить программу");
            System.out.print("Введите номер действия: ");
            int action = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            switch (action) {
                case 1:
                    performFilter(scanner, dataDirectory);
                    break;
                case 2:
                    performSort(scanner, dataDirectory);
                    break;
                case 3:
                    addData(scanner, dataDirectory);
                    break;
                case 4:
                    viewFilesContent(scanner, dataDirectory);
                    break;
                case 5:
                    createNewFile(scanner, dataDirectory);
                    break;
                case 6:
                    deleteFile(scanner, dataDirectory);
                    break;
                case 7:
                    continueRunning = false;
                    System.out.println("Программа завершена.");
                    break;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }

            if (action != 7 && continueRunning) {
                if (getYesOrNo(scanner, "Хотите выполнить другое действие? (да/нет)")) {
                    continueRunning = true;
                } else {
                    continueRunning = false;
                    System.out.println("Программа завершена.");
                }
            }
        }

        scanner.close();
    }

    private static boolean getYesOrNo(Scanner scanner, String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine().trim().toLowerCase();

        if (input.isEmpty()) {
            return true; // Нажатие Enter считается как "да"
        }

        switch (input) {
            case "да":
            case "д":
            case "yes":
            case "y":
                return true;
            case "нет":
            case "н":
            case "no":
            case "n":
            case "esc":
                return false;
            default:
                System.out.println("Некорректный ввод. Попробуйте снова.");
                return getYesOrNo(scanner, prompt); // Повторить вопрос при некорректном вводе
        }
    }

    private static void performFilter(Scanner scanner, String dataDirectory) {
        File selectedFile = selectFileOrAll(scanner, dataDirectory);
        if (selectedFile != null) {
            DataProcessor processor = new DataProcessor();
            readDataFromFile(selectedFile.getPath(), processor);

            System.out.print("Введите префикс для фильтрации строк: ");
            String filterPrefix = scanner.nextLine().trim().toLowerCase(); // Удаление пробелов и перевод в нижний регистр
            processor.filterDataByPrefix(filterPrefix);

            System.out.println("Отфильтрованные данные:");
            for (String str : processor.getData()) {
                System.out.println(str);
            }

            processor.analyzeData();
        } else {
            for (File file : new File(dataDirectory).listFiles()) {
                if (file.isFile()) {
                    DataProcessor processor = new DataProcessor();
                    readDataFromFile(file.getPath(), processor);
                    System.out.print("Введите префикс для фильтрации строк файла " + file.getName() + ": ");
                    String filterPrefix = scanner.nextLine().trim().toLowerCase(); // Удаление пробелов и перевод в нижний регистр
                    processor.filterDataByPrefix(filterPrefix);
                    System.out.println("Отфильтрованные данные файла " + file.getName() + ":");
                    for (String str : processor.getData()) {
                        System.out.println(str);
                    }
                    processor.analyzeData();
                }
            }
        }
    }

    private static void performSort(Scanner scanner, String dataDirectory) {
        File selectedFile = selectFileOrAll(scanner, dataDirectory);
        if (selectedFile != null) {
            DataProcessor processor = new DataProcessor();
            readDataFromFile(selectedFile.getPath(), processor);

            System.out.print("Выберите сортировку (1 - по алфавиту, 2 - в обратном порядке): ");
            int sortOption = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            if (sortOption == 1) {
                processor.sortData();
            } else if (sortOption == 2) {
                processor.sortDataReverse();
            }

            System.out.println("Отсортированные данные:");
            for (String str : processor.getData()) {
                System.out.println(str);
            }

            processor.analyzeData();
        } else {
            for (File file : new File(dataDirectory).listFiles()) {
                if (file.isFile()) {
                    DataProcessor processor = new DataProcessor();
                    readDataFromFile(file.getPath(), processor);

                    System.out.print("Выберите сортировку (1 - по алфавиту, 2 - в обратном порядке): ");
                    int sortOption = scanner.nextInt();
                    scanner.nextLine(); // очистка буфера

                    if (sortOption == 1) {
                        processor.sortData();
                    } else if (sortOption == 2) {
                        processor.sortDataReverse();
                    }

                    System.out.println("Отсортированные данные файла " + file.getName() + ":");
                    for (String str : processor.getData()) {
                        System.out.println(str);
                    }

                    processor.analyzeData();
                }
            }
        }
    }

    private static void addData(Scanner scanner, String dataDirectory) {
        File selectedFile = selectFileOrAll(scanner, dataDirectory);
        if (selectedFile != null) {
            System.out.print("Введите строку для добавления: ");
            String newData = scanner.nextLine().trim(); // Удаление пробелов в начале и конце строки
            UserInputExample.saveDataToFile(selectedFile.getPath(), newData);
        } else {
            for (File file : new File(dataDirectory).listFiles()) {
                if (file.isFile()) {
                    System.out.print("Введите строку для добавления в файл " + file.getName() + ": ");
                    String newData = scanner.nextLine().trim();
                    UserInputExample.saveDataToFile(file.getPath(), newData);
                }
            }
        }
    }

    private static void viewFilesContent(Scanner scanner, String dataDirectory) {
        File selectedFile = selectFileOrAll(scanner, dataDirectory);
        if (selectedFile != null) {
            System.out.println("Содержимое файла " + selectedFile.getName() + ":");
            printFileContent(selectedFile.getPath());
        } else {
            for (File file : new File(dataDirectory).listFiles()) {
                if (file.isFile()) {
                    System.out.println("Содержимое файла " + file.getName() + ":");
                    printFileContent(file.getPath());
                }
            }
        }
    }

    private static void createNewFile(Scanner scanner, String dataDirectory) {
        System.out.print("Введите имя нового файла: ");
        String newFileName = scanner.nextLine().trim();
        File newFile = new File(dataDirectory, newFileName);

        try {
            if (newFile.createNewFile()) {
                System.out.println("Файл создан: " + newFile.getPath());
            } else {
                System.out.println("Файл уже существует.");
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка при создании файла.");
            e.printStackTrace();
        }
    }

    private static void deleteFile(Scanner scanner, String dataDirectory) {
        File selectedFile = selectFileOrAll(scanner, dataDirectory);
        if (selectedFile != null) {
            if (selectedFile.delete()) {
                System.out.println("Файл удален: " + selectedFile.getPath());
            } else {
                System.out.println("Не удалось удалить файл.");
            }
        } else {
            for (File file : new File(dataDirectory).listFiles()) {
                if (file.isFile()) {
                    if (file.delete()) {
                        System.out.println("Файл " + file.getName() + " удален.");
                    } else {
                        System.out.println("Не удалось удалить файл " + file.getName() + ".");
                    }
                }
            }
        }
    }

    private static File selectFileOrAll(Scanner scanner, String dataDirectory) {
        File directory = new File(dataDirectory);
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("Нет файлов в директории " + dataDirectory);
            return null;
        }

        System.out.println("Выберите файл или выберите все файлы:");
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + " - " + files[i].getName());
        }
        System.out.println((files.length + 1) + " - Все файлы");
        System.out.print("Введите номер: ");
        int fileIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // очистка буфера

        if (fileIndex == files.length) {
            return null; // Вернуть null, если выбраны все файлы
        } else if (fileIndex >= 0 && fileIndex < files.length) {
            return files[fileIndex];
        } else {
            System.out.println("Некорректный выбор. Попробуйте снова.");
            return selectFileOrAll(scanner, dataDirectory);
        }
    }

    private static void printFileContent(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка при чтении файла.");
            e.printStackTrace();
        }
    }

    private static void readDataFromFile(String filePath, DataProcessor processor) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                processor.addData(line);
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка при чтении файла: " + filePath);
        }
    }
}
