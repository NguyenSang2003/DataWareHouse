package dataWareHouse;

import com.opencsv.CSVWriter;
import ult.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Crawler {

	public void crawlLotteryData(String csvFile, String startDate, String endDate) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar = Calendar.getInstance();

		// Parse start date
		try {
			Date start = sdf.parse(startDate);
			calendar.setTime(start);
		} catch (Exception e) {
			Logger.log("Lỗi định dạng ngày bắt đầu: " + e.getMessage());
			throw new IllegalArgumentException("Ngày bắt đầu không hợp lệ.");
		}

		// Parse end date
		Date end;
		try {
			end = sdf.parse(endDate);
		} catch (Exception e) {
			Logger.log("Lỗi định dạng ngày kết thúc: " + e.getMessage());
			throw new IllegalArgumentException("Ngày kết thúc không hợp lệ.");
		}

		// Tạo writer để ghi vào file CSV với BOM
		try (Writer writer1 = new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8")) {
			writer1.write('\uFEFF'); // Ghi BOM vào file
			writer1.flush();

			try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
				// Ghi header vào CSV
				String[] header = { "SampleDate", "Province", "Prize8", "Prize7", "Prize6_3", "Prize6_2", "Prize6_1",
						"Prize5", "Prize4_7", "Prize4_6", "Prize4_5", "Prize4_4", "Prize4_3", "Prize4_2", "Prize4_1",
						"Prize3_2", "Prize3_1", "Prize2", "Prize1", "SpecialPrize" };
				writer.writeNext(header);
				Logger.log("Đã ghi header vào file CSV.");

				// Lặp qua các ngày trong khoảng thời gian
				while (!calendar.getTime().after(end)) {
					String dateString = sdf.format(calendar.getTime());
					String url = "https://www.minhngoc.net.vn/ket-qua-xo-so/" + dateString + ".html";

					try {
						Document doc = Jsoup.connect(url).get();
						Elements dateElements = doc.select("td.ngay a");

						for (Element dateElement : dateElements) {
							String sampleDate = dateElement.text().trim();
							if (sampleDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
								Date date = dateFormat.parse(sampleDate);
								Calendar cal = Calendar.getInstance();
								cal.setTime(date);
								int year = cal.get(Calendar.YEAR);
								int month = cal.get(Calendar.MONTH);

								if (year == calendar.get(Calendar.YEAR) && month == calendar.get(Calendar.MONTH)) {
									Logger.log("Đang xử lý ngày: " + sampleDate);
									Elements tables = doc.select("table.rightcl");

									for (Element table : tables) {
										String province = table.select("td.tinh a").text();
										String prize8 = table.select("td.giai8").text();
										String prize7 = table.select("td.giai7").text();
										String[] prize6 = extractPrizes(table.select("td.giai6").text(), 3);
										String prize5 = table.select("td.giai5").text();
										String[] prize4 = extractPrizes(table.select("td.giai4").text(), 7);
										String[] prize3 = extractPrizes(table.select("td.giai3").text(), 2);
										String prize2 = table.select("td.giai2").text();
										String prize1 = table.select("td.giai1").text();
										String specialPrize = table.select("td.giaidb").text();

										String[] data = { sampleDate, province, prize8, prize7, prize6[2], prize6[1],
												prize6[0], prize5, prize4[6], prize4[5], prize4[4], prize4[3],
												prize4[2], prize4[1], prize4[0], prize3[1], prize3[0], prize2, prize1,
												specialPrize };
										writer.writeNext(data);
										Logger.log("Đã ghi dữ liệu cho tỉnh: " + province);
									}
								}
							}
						}
					} catch (Exception e) {
						Logger.log("Không có kết quả cho ngày: " + dateString + ". Lỗi: " + e.getMessage());
					}

					calendar.add(Calendar.DAY_OF_MONTH, 1);
				}

			} catch (IOException e) {
				Logger.log("Lỗi khi ghi vào file CSV: " + e.getMessage());
				throw e;
			}
		}
	}

	private String[] extractPrizes(String prizesText, int count) {
		String[] splitPrizes = prizesText.split(" ");
		String[] prizes = new String[count];
		for (int i = 0; i < prizes.length; i++) {
			prizes[i] = (i < splitPrizes.length) ? splitPrizes[i] : "";
		}
		return prizes;
	}
}