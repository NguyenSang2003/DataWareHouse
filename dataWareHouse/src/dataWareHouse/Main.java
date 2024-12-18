package dataWareHouse;

import java.io.IOException;
import java.sql.SQLException;

import ult.DatabaseManager;

import ult.Logger;


import dataWareHouse.*;


public class Main {


	public static void main(String[] args) throws IOException, SQLException {

//	b1: kết nối DB --> b2: crawl data --> b3: Load Staging --> b4: transform data --> b5: in lên GUI
		ETLData etl = new ETLData();
		Logger log = new Logger();
		// Bước 1: Kết nối đến cơ sở dữ liệu
		DatabaseManager.connectToDatabase();

		// Bước 2: Crawl data từ web về
		Crawler crawler = new Crawler();
		crawler.crawlLotteryData("ket_qua_xo_so_t9.csv", "01-09-2024", "30-09-2024");
    
		// Bước 3: Load stagging
		etl.setDateFormat();
		// 3.1 load craw data staging
		if (etl.loadCrawData_Staging()) {
			// 3.1.2
			log.log("3.1.2: load craw data staging succecss");
		} else {
			// 3.1.1
			log.log("3.1.1: load craw data staging faild");
		}
		// 3.2 load craw data
		if (etl.loadCrawData()) {
			// 3.2.2
			log.log("3.2.2: load craw data succecss");
		} else {
			// 3.2.1
			log.log("3.2.1: load craw data faild");
		}
		// 3.3 load staging
		if (etl.loadStaging()) {
			// 3.3.2
			log.log("3.3.2: load staging succecss");
		} else {
			// 3.3.1
			log.log("3.3.1: load staging faild");
		}
		// Bước 4: Transform data
		// 4.1 insert DimDate
		if (etl.insertDimDate()) {
			// 4.1.2
			log.log("4.1.2: insert DimDate succecss");
		} else {
			// 4.1.1
			log.log("4.1.1: insert DimDate faild");
		}
		// 4.2 insert DimLocation
		if (etl.insertDimLocation()) {
			// 4.2.2
			log.log("4.2.2: insert DimLocation succecss");
		} else {
			// 4.2.1
			log.log("4.2.1: insert DimLocation faild");
		}
		// 4.3 insert FactPrize
		if (etl.insertFactPrize()) {
			// 4.3.2
			log.log("4.3.2: insert FactPrize succecss");
		} else {
			// 4.3.1
			log.log("4.3.1: load FactPrize faild");
		}

		// Bước 5: Hiển thị kết quả lên giao diện GUI
		LotteryResultGUI gui = new LotteryResultGUI();
		gui.createAndShowGUI();
	}
}
