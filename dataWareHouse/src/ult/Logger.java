package ult;

import java.time.LocalDateTime;

import static ult.DatabaseManager.saveLog;
public class Logger {
    private static final EmailNotifier emailNotifier = new EmailNotifier();
    // Phương thức ghi log
    public static void log(String message) {
        // In ra console
        System.out.println(LocalDateTime.now() + ": " + message);

        // Lưu log vào database
        boolean check = saveLog(message);

        // Gửi email nếu là lỗi
        if (!check) {
            String errorMessage = "Lỗi khi ghi log vào cơ sở dữ liệu: " + message;
            emailNotifier.sendErrorLog(errorMessage);
        }
    }
}