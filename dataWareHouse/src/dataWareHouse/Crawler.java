package dataWareHouse;

import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Crawler {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();

        // Bắt đầu từ ngày 1/9/2024
        calendar.set(2024, Calendar.SEPTEMBER, 1);

        // Đường dẫn đến file CSV
        String csvFile = "ket_qua_xo_so_t9_2.csv";

        // Tạo writer để ghi vào file CSV
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
            // Ghi header vào CSV
            String[] header = {"Ngày", "Tỉnh", "Giải 8", "Giải 7", 
                               "Giải 6-1", "Giải 6-2", "Giải 6-3", 
                               "Giải 5", "Giải 4-1", "Giải 4-2", "Giải 4-3", 
                               "Giải 4-4", "Giải 4-5", "Giải 4-6", "Giải 4-7",
                               "Giải 3-1", "Giải 3-2", 
                               "Giải 2", "Giải 1", "Giải Đặc Biệt"};
            writer.writeNext(header);

            // Lặp qua các ngày trong tháng 9
            while (true) {
                String dateString = sdf.format(calendar.getTime());
                String url = "https://www.minhngoc.net.vn/ket-qua-xo-so/" + dateString + ".html"; 

                // Kiểm tra nếu tháng hiện tại là tháng 9
                if (calendar.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
                    try {
                        Document doc = Jsoup.connect(url).get();
                        String dateTitle = doc.select("h1").text();

                        // In tiêu đề cho ngày hiện tại
                        System.out.println("Tiêu đề ngày: " + dateTitle);

                        // Chỉ in tiêu đề nếu nó không chứa "Tra Cứu Kết Quả Xổ Số"
                        if (!dateTitle.contains("Tra Cứu Kết Quả Xổ Số")) {
                            System.out.println("Kết quả xổ số ngày: " + dateTitle);
                        }

                        Elements tables = doc.select("table.rightcl");
                        for (Element table : tables) {
                            // Lấy tên tỉnh
                            String province = table.select("td.tinh a").text();
                            System.out.println("Tỉnh: " + province);

                            // Lấy từng giải từ 8 đến Đặc Biệt
                            String Giai8 = table.select("td.giai8").text();
                            String Giai7 = table.select("td.giai7").text();

                            // Xử lý giải 6 và tách thành các cột riêng
                            String[] splitGiai6 = table.select("td.giai6").text().split(" ");
                            String[] Giai6 = new String[3];
                            for (int i = 0; i < Giai6.length; i++) {
                                Giai6[i] = (i < splitGiai6.length) ? splitGiai6[i] : "";
                            }

                            String Giai5 = table.select("td.giai5").text();

                            // Xử lý giải 4 và tách thành các cột riêng
                            String[] splitGiai4 = table.select("td.giai4").text().split(" ");
                            String[] Giai4 = new String[7];
                            for (int i = 0; i < Giai4.length; i++) {
                                Giai4[i] = (i < splitGiai4.length) ? splitGiai4[i] : "";
                            }

                            // Xử lý giải 3 và tách thành các cột riêng
                            String[] splitGiai3 = table.select("td.giai3").text().split(" ");
                            String[] Giai3 = new String[2];
                            for (int i = 0; i < Giai3.length; i++) {
                                Giai3[i] = (i < splitGiai3.length) ? splitGiai3[i] : "";
                            }

                            String Giai2 = table.select("td.giai2").text();
                            String Giai1 = table.select("td.giai1").text();
                            String GiaiDB = table.select("td.giaidb").text();

                            // Ghi dữ liệu vào CSV
//                            String[] data = {
//                                dateTitle,
//                                province,
//                                Giai8,
//                                Giai7,
//                                Giai6[0], Giai6[1], Giai6[2],
//                                Giai5,
//                                Giai4[0], Giai4[1], Giai4[2], Giai4[3], Giai4[4], Giai4[5], Giai4[6],
//                                Giai3[0], Giai3[1],
//                                Giai2,
//                                Giai1,
//                                GiaiDB
//                            };
                            String[] data = {
                            	    dateTitle,
                            	    province,
                            	    "'" + Giai8,    
                            	    "'" + Giai7,
                            	    "'" + Giai6[0], "'" + Giai6[1], "'" + Giai6[2],
                            	    "'" + Giai5,
                            	    "'" + Giai4[0], "'" + Giai4[1], "'" + Giai4[2], "'" + Giai4[3], "'" + Giai4[4], "'" + Giai4[5], "'" + Giai4[6],
                            	    "'" + Giai3[0], "'" + Giai3[1],
                            	    "'" + Giai2,
                            	    "'" + Giai1,
                            	    "'" + GiaiDB
                            	};
                            writer.writeNext(data);

                            System.out.println("=================");
                        }
                    } catch (Exception e) {
                        System.out.println("Không có kết quả cho ngày: " + dateString);
                    }
                }

                // Tăng ngày lên 1
                calendar.add(Calendar.DAY_OF_MONTH, 1);

                // Dừng lại nếu đến ngày 1/10/2024
                if (calendar.get(Calendar.MONTH) == Calendar.OCTOBER && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi vào file CSV: " + e.getMessage());
        }
    }
}
