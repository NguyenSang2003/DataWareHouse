package dataWareHouse;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static final String LOG_FILE = "application.log";

    // Ghi log với trạng thái thành công
    public static void logSuccess(String message) {
        log("SUCCESS", message);
    }

    // Ghi log với trạng thái thất bại
    public static void logFailure(String message) {
        log("FAILURE", message);
    }

    // Ghi log chung
    private static void log(String status, String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.printf("[%s] [%s] %s%n", timestamp, status, message);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
    	  Crawler crawler = new Crawler();
          EmailNotifier emailNotifier = new EmailNotifier();

          try {
              // Bước 1: Lấy data
              Logger.logSuccess("Step 1: Data retrieval started.");
              crawler.main(null); // Gọi phương thức chính để crawl dữ liệu
              Logger.logSuccess("Step 1: Data retrieved successfully.");

              // Bước 2: Ghi dữ liệu vào file CSV
              Logger.logSuccess("Step 2: Writing data to CSV started.");
              // CSV đã được ghi bởi `crawler.main`, ghi log thành công
              Logger.logSuccess("Step 2: Data written to CSV successfully.");

              // Bước 3: Kiểm tra lưu trữ tại vị trí cụ thể (giả định kiểm tra file CSV tồn tại)
              Logger.logSuccess("Step 3: Validating saved data started.");
              String filePath = "ket_qua_xo_so_t9.csv"; // Vị trí file CSV
              if (new java.io.File(filePath).exists()) {
                  Logger.logSuccess("Step 3: File saved successfully.");
              } else {
                  throw new Exception("File not found at: " + filePath);
              }

          } catch (Exception e) {
              // Ghi log thất bại và gửi email khi có lỗi
              String errorMessage = "Process failed: " + e.getMessage();
              Logger.logFailure(errorMessage);
              emailNotifier.sendErrorNotification(errorMessage);
          }
      }
	}