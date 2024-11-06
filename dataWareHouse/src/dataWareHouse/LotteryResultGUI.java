package dataWareHouse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class LotteryResultGUI {
	// with data
	private static final String DB_URL = "jdbc:sqlserver://DESKTOP-CDO0SQ2\\MAYAO;databaseName=LotteryResultsWithData;user=sang;password=sang;encrypt=true;trustServerCertificate=true";

	// no data
//	private static final String DB_URL = "jdbc:sqlserver://DESKTOP-CDO0SQ2\\MAYAO;databaseName=LotteryResultsNoData;user=sang;password=sang;encrypt=true;trustServerCertificate=true";

	// fail connect
//	private static final String DB_URL = "jdbc:sqlserver://DESKTOP-CDO0SQ2\\MAYAO;databaseName=LotterResltsWithData;user=sang;password=sang;encrypt=true;trustServerCertificate=true";

	private final EmailNotifier emailNotifier = new EmailNotifier();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			LotteryResultGUI gui = new LotteryResultGUI();
			gui.createAndShowGUI();
		});
	}

	// Phương thức để tạo giao diện
	private void createAndShowGUI() {
		JFrame frame = new JFrame("Southern Vietnam Lottery Results");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 600);

		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);

		loadData(tableModel); // Tải dữ liệu từ cơ sở dữ liệu

		JScrollPane scrollPane = new JScrollPane(table);
		frame.add(scrollPane);
		frame.setVisible(true);
	}

	// Phương thức loadData
	private void loadData(DefaultTableModel tableModel) {
		System.out.println("Begin to load data from database...");

		try (Connection conn = DriverManager.getConnection(DB_URL)) {
			System.out.println("Database connected successfully");

			// Cập nhật truy vấn để lấy dữ liệu từ FactPrize sử dụng các cột DateID và
			// LocationID từ bảng khác để lấy dateid và locationid thành tên
//			String query = "SELECT d.DateOfTake, l.LocationName, "
//					+ "f.Prize8, f.Prize7, f.Prize6_1, f.Prize6_2, f.Prize6_3, f.Prize5, "
//					+ "f.Prize4_1, f.Prize4_2, f.Prize4_3, f.Prize4_4, f.Prize4_5, f.Prize4_6, f.Prize4_7, "
//					+ "f.Prize3_1, f.Prize3_2, f.Prize2, f.Prize1, f.SpecialPrize " + "FROM FactPrize f "
//					+ "JOIN DimDate d ON f.DateID = d.DateID " + "JOIN DimLocation l ON f.LocationID = l.LocationID";

			// Truy vấn để lấy tất cả dữ liệu từ FactPrize
			String query = "SELECT DateID, LocationID, SpecialPrize, Prize1, Prize2, Prize3_1, Prize3_2, "
					+ "Prize4_1, Prize4_2, Prize4_3, Prize4_4, Prize4_5, Prize4_6, Prize4_7, "
					+ "Prize5, Prize6_1, Prize6_2, Prize6_3, Prize7, Prize8 FROM FactPrize";

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// Lấy tên cột từ metadata của ResultSet
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<>();
			for (int i = 1; i <= columnCount; i++) {
				columnNames.add(metaData.getColumnName(i));
			}
			tableModel.setColumnIdentifiers(columnNames);

			// Kiểm tra xem có dữ liệu hay không, nếu không có gửi email
			if (!rs.isBeforeFirst()) {
				JOptionPane.showMessageDialog(null, "Không có dữ liệu từ cơ sở dữ liệu.", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
				System.out.println("No data to show.");

				// Gửi email thông báo không có dữ liệu
				EmailNotifier emailNotifier = new EmailNotifier();
				emailNotifier.sendNoDataNotification();

				return;
			}

			// Thêm các hàng vào table model
			while (rs.next()) {
				Vector<Object> row = new Vector<>();
				for (int i = 1; i <= columnCount; i++) {
					row.add(rs.getObject(i));
				}
				tableModel.addRow(row);
			}

			System.out.println("Data loaded successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error loading data from the database: " + e.getMessage());

			// Gửi email khi xảy ra lỗi
			emailNotifier.sendErrorNotification(e.getMessage());

			JOptionPane.showMessageDialog(null, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi cơ sở dữ liệu",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}