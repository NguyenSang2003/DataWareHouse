package dataWareHouse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class LotteryResultGUI {
	private static final String DB_URL = "jdbc:sqlserver://DESKTOP-CDO0SQ2\\MAYAO;databaseName=LotteryResults;user=sang;password=sang;encrypt=true;trustServerCertificate=true";

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

	// Phương thức non-static, có thể sử dụng DB_URL instance
	private void loadData(DefaultTableModel tableModel) {
		System.out.println("Begin to load data from database...");

		try (Connection conn = DriverManager.getConnection(DB_URL)) {
			System.out.println("Database connect success");

			// Thực hiện truy vấn để lấy dữ liệu từ các bảng FactPrize, DimDate và
			// DimLocation
			String query = "SELECT d.DateOfTake, l.LocationName, "
					+ "s.Prize8, s.Prize7, s.Prize6_1, s.Prize6_2, s.Prize6_3, s.Prize5, "
					+ "s.Prize4_1, s.Prize4_2, s.Prize4_3, s.Prize4_4, s.Prize4_5, s.Prize4_6, s.Prize4_7, "
					+ "s.Prize3_1, s.Prize3_2, s.Prize2, s.Prize1, s.SpecialPrize " + "FROM StagingPrize s "
					+ "JOIN DimDate d ON s.SampleDate = d.DateOfTake "
					+ "JOIN DimLocation l ON s.Province = l.LocationName";

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

			// Kiểm tra xem có dữ liệu hay không
			if (!rs.isBeforeFirst()) {
				JOptionPane.showMessageDialog(null, "Không có dữ liệu từ cơ sở dữ liệu.", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
				System.out.println("No data to show.");
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

			System.out.println("Data show success.");

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi cơ sở dữ liệu",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}