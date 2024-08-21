import java.io.FileWriter;
import java.io.IOException;

public class UserInputExample {

    public static void saveDataToFile(String filePath, String data) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(data + "\n");
            System.out.println("Данные сохранены в файл: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
