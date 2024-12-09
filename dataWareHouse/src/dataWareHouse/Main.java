package dataWareHouse;

import java.sql.SQLException;

import javax.swing.SwingUtilities;

import ult.DatabaseManager;
import ult.Logger;

public class Main {

//	b1: kết nối DB --> b2: crawl data --> b3: Load Staging --> b4: transform data --> b5: in lên GUI

	public static void main(String[] args) throws SQLException {

		// Bước 1: Kết nối đến cơ sở dữ liệu
		DatabaseManager.connectToDatabase();
		Logger logger = new Logger();
		logger.log("Heloo");

		// Bước 2: crawl data từ web về

		// Bước 3: Load stagging

		// Bước 4: Transform data

		// Bước 5: Khởi tạo giao diện người dùng để hiển thị kết quả xổ số
		LotteryResultGUI gui = new LotteryResultGUI();
		gui.createAndShowGUI();
	}
}
