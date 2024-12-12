package dataWareHouse;

import java.io.IOException;
import java.sql.SQLException;

import ult.DatabaseManager;

public class Main {

	public static void main(String[] args) throws IOException, SQLException {
		// Bước 1: Kết nối đến cơ sở dữ liệu
		DatabaseManager.connectToDatabase();

		// Bước 2: Crawl data từ web về
		Crawler crawler = new Crawler();
		crawler.crawlLotteryData("ket_qua_xo_so_t9.csv", "01-09-2024", "30-09-2024");

		// Bước 3: Load staging
		// Implement logic here

		// Bước 4: Transform data
		// Implement logic here

		// Bước 5: Hiển thị kết quả lên giao diện GUI
		LotteryResultGUI gui = new LotteryResultGUI();
		gui.createAndShowGUI();
	}
}
