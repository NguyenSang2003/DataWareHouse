package ult;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
	private final static EmailNotifier emailNotifier = new EmailNotifier();
	private static Connection sharedConnection = null;

	// Đọc thông tin kết nối từ file config.properties
	private static String DB_URL;

	static {
		try {
			// B1 Đọc thông tin cấu hình từ file properties
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream("config.properties");
			properties.load(inputStream);

			// Lấy thông tin kết nối từ file cấu hình
			String dbUrl = properties.getProperty("db.url");
			String dbName = properties.getProperty("db.name");
			String dbUser = properties.getProperty("db.user");
			String dbPassword = properties.getProperty("db.password");
			String dbEncrypt = properties.getProperty("db.encrypt");
			String dbTrustServerCertificate = properties.getProperty("db.trustServerCertificate");

			// Tạo chuỗi kết nối đầy đủ
			DB_URL = String.format("%s;databaseName=%s;user=%s;password=%s;encrypt=%s;trustServerCertificate=%s", dbUrl,
					dbName, dbUser, dbPassword, dbEncrypt, dbTrustServerCertificate);

			System.out.println("B1: Loading database configuration success.");
		} catch (IOException e) {
			System.err.println("B1: Error loading database configuration: " + e.getMessage());
			emailNotifier.sendErrorNotification("B1: Error loading database configuration: " + e.getMessage());
		}
	}

	// B1. Hàm kết nối đến cơ sở dữ liệu và trả về Connection
	public static Connection connectToDatabase() throws SQLException {
		if (sharedConnection != null && !sharedConnection.isClosed()) {
			return sharedConnection; // Trả về kết nối đã được thiết lập
		}

		try {
			sharedConnection = DriverManager.getConnection(DB_URL);
			System.out.println("B1.1: Database connected successfully");
			return sharedConnection;
		} catch (SQLException e) {
			System.out.println("B1.1: Failed to connect to the database: " + e.getMessage());
			emailNotifier.sendErrorNotification("B1.1: Failed to connect to the database: " + e.getMessage());
			throw e;
		}
	}

	// Hàm lấy dữ liệu từ cơ sở dữ liệu
	public static ResultSet loadDataFromDatabase() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// Kết nối cơ sở dữ liệu
			conn = connectToDatabase();

			// Truy vấn cơ sở dữ liệu
			String query = "SELECT DateID, LocationID, SpecialPrize, Prize1, Prize2, Prize3_1, Prize3_2, "
					+ "Prize4_1, Prize4_2, Prize4_3, Prize4_4, Prize4_5, Prize4_6, Prize4_7, "
					+ "Prize5, Prize6_1, Prize6_2, Prize6_3, Prize7, Prize8 FROM FactPrize";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			return rs; // Trả về kết quả truy vấn (ResultSet)

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error while querying the database", e);
		}
	}

	// Phương thức đóng kết nối và tài nguyên
	public static void closeSharedConnection() {
		try {
			if (sharedConnection != null && !sharedConnection.isClosed()) {
				sharedConnection.close();
				System.out.println("B1.2: Database connection closed.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
