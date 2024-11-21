package dataWareHouse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ult.DatabaseManager;
import ult.EmailNotifier;

import java.sql.*;
import java.util.Vector;

public class LotteryResultGUI {

	private final EmailNotifier emailNotifier = new EmailNotifier();

	public void createAndShowGUI() {
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

	private void loadData(DefaultTableModel tableModel) {
		try {
			ResultSet rs = DatabaseManager.loadDataFromDatabase(); // Gọi phương thức từ DatabaseManager

			// Lấy tên cột từ metadata của ResultSet
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<>();
			for (int i = 1; i <= columnCount; i++) {
				columnNames.add(metaData.getColumnName(i));
			}
			tableModel.setColumnIdentifiers(columnNames);

			// Kiểm tra xem có dữ liệu không
			if (!rs.isBeforeFirst()) {
				JOptionPane.showMessageDialog(null, "Không có dữ liệu từ cơ sở dữ liệu.", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
				System.out.println("No data to show.");

				// Gửi email thông báo không có dữ liệu
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

			System.out.println("B5.1: Data loaded successfully.");

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
