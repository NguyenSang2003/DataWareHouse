package dataWareHouse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String LOG_FILE = "crawler_log.txt"; // Tên file log

    // Ghi log thành công
    public static void logSuccess(String message) {
        log("SUCCESS", message);
    }

    // Ghi log thất bại
    public static void logFailure(String message) {
        log("FAILURE", message);
    }

    // Ghi log vào file với mức độ và thông điệp
    private static void log(String level, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("[" + timestamp + "] [" + level + "] " + message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
