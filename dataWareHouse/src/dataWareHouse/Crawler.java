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

//    public static void main(String[] args) throws IOException {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        Calendar calendar = Calendar.getInstance();
//
//        // Bắt đầu từ ngày 1/9/2024
//        calendar.set(2024, Calendar.SEPTEMBER, 1);
//
//        // Đường dẫn đến file CSV
//        String csvFile = "ket_qua_xo_so_t9.csv";
//
//       
//
//        // Tạo writer để ghi vào file CSV với BOM
//        try (Writer writer1 = new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8")) {
//          // Ghi BOM vào file
//          writer1.write('\uFEFF');
//          writer1.flush();
//      // Tạo writer để ghi vào file CSV
//      try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
//          // Ghi header vào CSV
//    	  String[] header = {
//    			  	"SampleDate", "Province", 
//    			    "Prize8", "Prize7", 
//    			    "Prize6_3", "Prize6_2", "Prize6_1", 
//    			    "Prize5", 
//    			    "Prize4_7", "Prize4_6", "Prize4_5", "Prize4_4", "Prize4_3", "Prize4_2", "Prize4_1", 
//    			    "Prize3_2", "Prize3_1", 
//    			    "Prize2", "Prize1", "SpecialPrize"
//    			};
//          writer.writeNext(header);
//          Logger.log("Đã ghi header vào file CSV.");
//            // Lặp qua các ngày trong tháng 9
//            while (true) {
//                String dateString = sdf.format(calendar.getTime());
//                String url = "https://www.minhngoc.net.vn/ket-qua-xo-so/" + dateString + ".html";
//
//                // Kiểm tra nếu tháng hiện tại là tháng 9
//                if (calendar.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
//                    try {
//                        Document doc = Jsoup.connect(url).get();
//                        Elements dateElements = doc.select("td.ngay a");
//
//                        for (Element dateElement : dateElements) {
//                            String SampleDate = dateElement.text().trim();
//
//                            // Kiểm tra định dạng và khoảng thời gian
//                            if (SampleDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
//                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                                Date date = dateFormat.parse(SampleDate);
//
//                                Calendar cal = Calendar.getInstance();
//                                cal.setTime(date);
//                                int year = cal.get(Calendar.YEAR);
//                                int month = cal.get(Calendar.MONTH);
//
//                                if (year == 2024 && month == Calendar.SEPTEMBER) {
//                                    System.out.println("Đang xử lý ngày: " + SampleDate);
//
//                                    // Lấy các bảng liên quan
//                                    Elements tables = doc.select("table.rightcl");
//                                    for (Element table : tables) {
//                                        // Lấy tên tỉnh
//                                        String province = table.select("td.tinh a").text();
//                                        System.out.println("Tỉnh: " + province);
//
//                                        // Lấy từng giải từ 8 đến Đặc Biệt
//                                        String Giai8 = table.select("td.giai8").text();
//                                        String Giai7 = table.select("td.giai7").text();
//
//                                        // Xử lý giải 6 và tách thành các cột riêng
//                                        String[] splitGiai6 = table.select("td.giai6").text().split(" ");
//                                        String[] Giai6 = new String[3];
//                                        for (int i = 0; i < Giai6.length; i++) {
//                                            Giai6[i] = (i < splitGiai6.length) ? splitGiai6[i] : "";
//                                        }
//
//                                        String Giai5 = table.select("td.giai5").text();
//
//                                        // Xử lý giải 4 và tách thành các cột riêng
//                                        String[] splitGiai4 = table.select("td.giai4").text().split(" ");
//                                        String[] Giai4 = new String[7];
//                                        for (int i = 0; i < Giai4.length; i++) {
//                                            Giai4[i] = (i < splitGiai4.length) ? splitGiai4[i] : "";
//                                        }
//
//                                        // Xử lý giải 3 và tách thành các cột riêng
//                                        String[] splitGiai3 = table.select("td.giai3").text().split(" ");
//                                        String[] Giai3 = new String[2];
//                                        for (int i = 0; i < Giai3.length; i++) {
//                                            Giai3[i] = (i < splitGiai3.length) ? splitGiai3[i] : "";
//                                        }
//
//                                        String Giai2 = table.select("td.giai2").text();
//                                        String Giai1 = table.select("td.giai1").text();
//                                        String GiaiDB = table.select("td.giaidb").text();
//
//                                        String[] data = {
//                                        		 SampleDate,
//                                        		    province,
//                                        		    Giai8,
//                                        		    Giai7,
//                                        		    Giai6[2], Giai6[1], Giai6[0],
//                                        		    Giai5,
//                                        		    Giai4[6], Giai4[5], Giai4[4], Giai4[3], Giai4[2], Giai4[1], Giai4[0],
//                                        		    Giai3[1], Giai3[0],
//                                        		    Giai2,
//                                        		    Giai1,
//                                        		    GiaiDB
//                                            };
//                                        writer.writeNext(data);
//
//                                        System.out.println("=================");
//                                        Logger.log("Đã ghi dữ liệu cho tỉnh: " + province);
//                                    }
//                                }
//                            }
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Không có kết quả cho ngày: " + dateString);
//                    }
//                }
//
//                // Tăng ngày lên 1
//                calendar.add(Calendar.DAY_OF_MONTH, 1);
//
//                // Dừng lại nếu đến ngày 1/10/2024
//                if (calendar.get(Calendar.MONTH) == Calendar.OCTOBER && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
//                    break;
//                }
//            }
//
//        } catch (IOException e) {
//        	 Logger.log("Lỗi khi ghi vào file CSV: " + e.getMessage());
//            System.out.println("Lỗi khi ghi vào file CSV: " + e.getMessage());
//           
//        }
//    }
//}
//    
///////////////////////////////////////////////////
	public static void crawlAndSaveLotteryData() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
     // Bắt đầu từ ngày 1/9/2024
        calendar.set(2024, Calendar.SEPTEMBER, 1);
     // Đường dẫn đến file CSV
        String csvFile = "ket_qua_xo_so_t9.csv";
     // Tạo writer để ghi vào file CSV với BOM
        try (Writer writer1 = new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8")) {
            writer1.write('\uFEFF');
            writer1.flush();
         // Tạo writer để ghi vào file CSV
            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            	// Ghi header vào CSV
                String[] header = {
                        "SampleDate", "Province", 
                        "Prize8", "Prize7", 
                        "Prize6_3", "Prize6_2", "Prize6_1", 
                        "Prize5", 
                        "Prize4_7", "Prize4_6", "Prize4_5", "Prize4_4", "Prize4_3", "Prize4_2", "Prize4_1", 
                        "Prize3_2", "Prize3_1", 
                        "Prize2", "Prize1", "SpecialPrize"
                };
                writer.writeNext(header);
                Logger.log("Đã ghi header vào file CSV.");
             // Lặp qua các ngày trong tháng 9
                while (true) {
                    String dateString = sdf.format(calendar.getTime());
                    String url = "https://www.minhngoc.net.vn/ket-qua-xo-so/" + dateString + ".html";
                    // Kiểm tra nếu tháng hiện tại là tháng 9
                    if (calendar.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
                        try {
                            Document doc = Jsoup.connect(url).get();
                            Elements dateElements = doc.select("td.ngay a");

                            for (Element dateElement : dateElements) {
                                String SampleDate = dateElement.text().trim();
                             // Kiểm tra định dạng và khoảng thời gian
                                if (SampleDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = dateFormat.parse(SampleDate);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(date);
                                    int year = cal.get(Calendar.YEAR);
                                    int month = cal.get(Calendar.MONTH);
                                 // Lấy các bảng liên quan
                                    if (year == 2024 && month == Calendar.SEPTEMBER) {
                                        Elements tables = doc.select("table.rightcl");
                                        for (Element table : tables) {
                                            String province = table.select("td.tinh a").text();
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

                                            String[] data = {
                                                    SampleDate, province, Giai8, Giai7,
                                                    Giai6[2], Giai6[1], Giai6[0],
                                                    Giai5,
                                                    Giai4[6], Giai4[5], Giai4[4], Giai4[3], Giai4[2], Giai4[1], Giai4[0],
                                                    Giai3[1], Giai3[0],
                                                    Giai2, Giai1, GiaiDB
                                            };
                                            writer.writeNext(data);
                                            Logger.log("Đã ghi dữ liệu cho tỉnh: " + province);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Logger.log("Không có kết quả cho ngày: " + dateString);
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
                Logger.log("Lỗi khi ghi vào file CSV: " + e.getMessage());
            }
        }
    }
}