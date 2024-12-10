package dataWareHouse;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class ETLData {
	private String url;
	private String username;;
	private String password;
	private String Path;

	public ETLData() {
		loadConfig();
	}

	private void loadConfig() {
		try (FileInputStream input = new FileInputStream("config.properties")) {
			Properties prop = new Properties();
			prop.load(input);

			// Đọc email và mật khẩu ứng dụng từ tệp config
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			Path = prop.getProperty("pathData");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to load configuration file.");
		}
	}

	public Connection getConnect() {

		Connection conn = null;
		try {
			// Kết nối đến SQL Server
			return DriverManager.getConnection(url, username, password);
//			System.out.println("Kết nối thành công!");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public boolean setDateFormat() {
		ETLData etl = new ETLData();
		Connection connection = null;
		try {
	        connection = etl.getConnect();
	        String sql = "SET DATEFORMAT ymd";
	        PreparedStatement pr = connection.prepareStatement(sql);
	        pr.execute(); 
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;

	}

// load CrawData
	public boolean loadCrawData(String Path) {
		ETLData etl = new ETLData();
		Path = "D:/DataWareHouse/Data/Prize31102024.csv";
		Connection connection = null;
		try {
			connection = etl.getConnect();
			String sql = "exec [dbo].[proc_addCrawData] ?;";
			PreparedStatement pr = connection.prepareStatement(sql);
			 pr.execute(); 
	        return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;

	}

//	4. load Staging
	public boolean loadStaging() {
		ETLData etl = new ETLData();
		Connection connection = null;
		try {
			connection = etl.getConnect();
			String sql = "exec [dbo].[proc_addStagingPrize]";
			PreparedStatement pr = connection.prepareStatement(sql);
			 pr.execute(); 
		        return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;

	}

//	 load DimDate
	public boolean loadDimDate() {
		ETLData etl = new ETLData();
		Connection connection = null;
		try {
			connection = etl.getConnect();
			String sql = "exec [dbo].[proc_addDimDate] ";
			PreparedStatement pr = connection.prepareStatement(sql);
			 pr.execute(); 
		        return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;

	}

// load DimLocation
	public boolean loadDimLocation() {
		ETLData etl = new ETLData();
		Connection connection = null;
		try {
			connection = etl.getConnect();
			String sql = "exec [dbo].[proc_addDimLocation]";
			PreparedStatement pr = connection.prepareStatement(sql);
			 pr.execute(); 
		        return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;

	}

// load DimDate
	public boolean loadFactPrize() {
		ETLData etl = new ETLData();
		Connection connection = null;
		try {
			connection = etl.getConnect();
			String sql = "exec [dbo].[proc_addFactPrize]";
			PreparedStatement pr = connection.prepareStatement(sql);
			 pr.execute(); 
		        return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;

	}

//	public void test() {
//		ETLData etl = new ETLData();
//		Connection con = null;
//		int i = 0;
//		try {
//			con = etl.getConnect();
//			String sql = "select * from DimDate";
//			PreparedStatement pr = con.prepareStatement(sql);
//			ResultSet rs = pr.executeQuery();
//			while(rs.next()) {
//				i++;
//				System.out.println(i);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
	
	public static void main(String[] args) {
		ETLData etl = new ETLData();
		etl.getConnect();
		
		
//		etl.test();
		String path = etl.Path;
		// set date format
		System.out.println("set date format...");
		if (etl.setDateFormat()) {
			System.out.println("set date format successfully.");
		} else {
			System.err.println("Failed to set date format");
		}
		
		// Load CrawData
		System.out.println("Loading Craw Data...");
		if (etl.loadCrawData(path)) {
			System.out.println("Craw Data loaded successfully.");
		} else {
			System.err.println("Failed to load Craw Data.");
		}
//
//		// Load Staging
//		System.out.println("Loading Staging Data...");
//		if (etl.loadStaging()) {
//			System.out.println("Staging Data loaded successfully.");
//		} else {
//			System.err.println("Failed to load Staging Data.");
//		}
//
//		// Load DimDate
//		System.out.println("Loading DimDate...");
//		if (etl.loadDimDate()) {
//			System.out.println("DimDate loaded successfully.");
//		} else {
//			System.err.println("Failed to load DimDate.");
//		}
//
//		// Load DimLocation
//		System.out.println("Loading DimLocation...");
//		if (etl.loadDimLocation()) {
//			System.out.println("DimLocation loaded successfully.");
//		} else {
//			System.err.println("Failed to load DimLocation.");
//		}
//
//		// Load FactPrize
//		System.out.println("Loading FactPrize...");
//		if (etl.loadFactPrize()) {
//			System.out.println("FactPrize loaded successfully.");
//		} else {
//			System.err.println("Failed to load FactPrize.");
//		}
//
//		System.out.println("ETL process completed.");
	}

}
