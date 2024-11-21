package ult;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailNotifier {

	private final String recipientEmail = "21130512@st.hcmuaf.edu.vn"; // email người nhận

	private String senderEmail;
	private String senderPassword;

	public EmailNotifier() {
		loadConfig();
	}

	private void loadConfig() {
		try (FileInputStream input = new FileInputStream("config.properties")) {
			Properties prop = new Properties();
			prop.load(input);

			// Đọc email và mật khẩu ứng dụng từ tệp config
			senderEmail = prop.getProperty("senderEmail");
			senderPassword = prop.getProperty("senderPassword");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("B1.2: Failed to load configuration file.");
		}
	}

//  gửi email khi lỗi ko kết nối tới csdl	
	public void sendErrorNotification(String errorMessage) {
		// Cấu hình thông tin cho SMTP server
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		// Tạo phiên gửi email với thông tin xác thực
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		});

		try {
			// Tạo nội dung email
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderEmail, "Southern Vietnam Lottery Results"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
			message.setSubject("Database Load Failure Notification");

//			message.setText("An error occurred while loading data from the database:\n" + errorMessage);

			String htmlContent = "<html><body>" + "<h2 style='color:#ff0000;'>Database Load Failure Notification</h2>"
					+ "<p><strong>Error Message:</strong></p>" + "<p><i>" + errorMessage + "</i></p>"
					+ "<p>Please check the database connection and try again.</p>" + "</body></html>";

			// Tạo nội dung email HTML
			message.setContent(htmlContent, "text/html");

			// Gửi email
			Transport.send(message);
			System.out.println("Error notification email sent successfully.");

		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			System.err.println("Failed to send error notification email: " + e.getMessage());
		}
	}

//	gửi email khi không có dữ liệu
	public void sendNoDataNotification() {
		// Cấu hình thông tin cho SMTP server
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		// Tạo phiên gửi email với thông tin xác thực
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		});

		try {
			// Tạo nội dung email
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderEmail, "Southern Vietnam Lottery Results"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
			message.setSubject("No Data Available Notification");

			String htmlContent = "<html><body>" + "<h2 style='color:#ff0000;'>No Data Available Notification</h2>"
					+ "<p>There was no data available when loading from the database.</p>"
					+ "<p>Please check the data source and ensure data is available.</p>" + "</body></html>";

			// Tạo nội dung email HTML
			message.setContent(htmlContent, "text/html");

			// Gửi email
			Transport.send(message);
			System.out.println("No data notification email sent successfully.");

		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			System.err.println("Failed to send no data notification email: " + e.getMessage());
		}
	}

}