package Warehouse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class Crawler {
    public static void main(String[] args) {
//        // Định dạng ngày tháng
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        Calendar calendar = Calendar.getInstance();
//
//        // Bắt đầu từ ngày 1/9/2024
//        calendar.set(2024, Calendar.SEPTEMBER, 1);
//
//        Set<String> southernProvinces = new HashSet<>();
//        southernProvinces.add("TP. HCM");
//        southernProvinces.add("Đồng Nai");
//        southernProvinces.add("Bà Rịa - Vũng Tàu");
//        southernProvinces.add("Tây Ninh");
//        southernProvinces.add("Long An");
//        southernProvinces.add("Tiền Giang");
//        southernProvinces.add("Bến Tre");
//        southernProvinces.add("Trà Vinh");
//        southernProvinces.add("Vĩnh Long");
//        southernProvinces.add("Đồng Tháp");
//        southernProvinces.add("An Giang");
//        southernProvinces.add("Kiên Giang");
//        southernProvinces.add("Sóc Trăng");
//        southernProvinces.add("Hậu Giang");
//        southernProvinces.add("Bạc Liêu");
//        southernProvinces.add("Cà Mau");
//        southernProvinces.add("Đà Lạt");
//        southernProvinces.add("Cần Thơ");
//        southernProvinces.add("Bình Dương");
//        southernProvinces.add("Bình Phước");
//        southernProvinces.add("Bình Thuận");
//
//        // Lặp từ ngày 1/9/2024 đến ngày 1/10/2024
//        while (true) {
//            String dateString = sdf.format(calendar.getTime());
//            String url = "https://www.minhngoc.net.vn/ket-qua-xo-so/" + dateString + ".html"; 
//
//            // Kiểm tra nếu tháng hiện tại là tháng 9
//            if (calendar.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
//                try {
//                    Document doc = Jsoup.connect(url).get();
//                    String dateTitle = doc.select("h1").text();
//                    
//                    // In tiêu đề cho ngày hiện tại
//                    System.out.println("Tiêu đề ngày: " + dateTitle);
//                    
//                    // Chỉ in tiêu đề nếu nó không chứa "Tra Cứu Kết Quả Xổ Số"
//                    if (!dateTitle.contains("Tra Cứu Kết Quả Xổ Số")) {
//                        System.out.println("Kết quả xổ số ngày: " + dateTitle);
//                    }
//
//                    Elements tables = doc.select("table.rightcl");
//                    for (Element table : tables) {
//                        // Lấy tên tỉnh
//                        String province = table.select("td.tinh a").text();
//
//                        // Kiểm tra nếu tỉnh thuộc miền Nam
//                        if (southernProvinces.contains(province)) {
//                            System.out.println("Tỉnh: " + province);
//
//                            // Lấy từng giải từ 8 đến Đặc Biệt
//                            String Giai8 = table.select("td.giai8").text();
//                            System.out.println("Giải 8: " + Giai8);
//                            String Giai7 = table.select("td.giai7").text();
//                            System.out.println("Giải 7: " + Giai7);
//                            System.out.print("Giải 6: ");
//                            Elements Giai6 = table.select("td.giai6");
//                            for (Element g6 : Giai6) {
//                                System.out.print(g6.text() + " ");
//                            }
//                            System.out.println();
//
//                            String Giai5 = table.select("td.giai5").text();
//                            System.out.println("Giải 5: " + Giai5);
//                            System.out.print("Giải 4: ");
//                            Elements Giai4 = table.select("td.giai4");
//                            for (Element g4 : Giai4) {
//                                System.out.print(g4.text() + " ");
//                            }
//                            System.out.println();
//
//                            System.out.print("Giải 3: ");
//                            Elements Giai3 = table.select("td.giai3");
//                            for (Element g3 : Giai3) {
//                                System.out.print(g3.text() + " ");
//                            }
//                            System.out.println();
//
//                            String Giai2 = table.select("td.giai2").text();
//                            String Giai1 = table.select("td.giai1").text();
//                            String GiaiDB = table.select("td.giaidb").text();
//
//                            // In tất cả kết quả theo thứ tự từ Giải 8 đến Giải Đặc Biệt
//                            System.out.println("Giải 2: " + Giai2);
//                            System.out.println("Giải 1: " + Giai1);
//                            System.out.println("Giải Đặc Biệt: " + GiaiDB);
//                            System.out.println("=================");
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("Không có kết quả cho ngày: " + dateString);
//                }
//            }
//
//            // Tăng ngày lên 1
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//
//            // Dừng lại nếu đến ngày 1/10/2024
//            if (calendar.get(Calendar.MONTH) == Calendar.OCTOBER && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
//                break;
//            }
//        }
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();

        // Bắt đầu từ ngày 1/9/2024
        calendar.set(2024, Calendar.SEPTEMBER, 1);

        // Lặp từ ngày 1/9/2024 đến ngày 1/10/2024
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
                        System.out.println("Giải 8: " + Giai8);
                        String Giai7 = table.select("td.giai7").text();
                        System.out.println("Giải 7: " + Giai7);
                        System.out.print("Giải 6: ");
                        Elements Giai6 = table.select("td.giai6");
                        for (Element g6 : Giai6) {
                            System.out.print(g6.text() + " ");
                        }
                        System.out.println();

                        String Giai5 = table.select("td.giai5").text();
                        System.out.println("Giải 5: " + Giai5);
                        System.out.print("Giải 4: ");
                        Elements Giai4 = table.select("td.giai4");
                        for (Element g4 : Giai4) {
                            System.out.print(g4.text() + " ");
                        }
                        System.out.println();

                        System.out.print("Giải 3: ");
                        Elements Giai3 = table.select("td.giai3");
                        for (Element g3 : Giai3) {
                            System.out.print(g3.text() + " ");
                        }
                        System.out.println();

                        String Giai2 = table.select("td.giai2").text();
                        String Giai1 = table.select("td.giai1").text();
                        String GiaiDB = table.select("td.giaidb").text();

                        // In tất cả kết quả theo thứ tự từ Giải 8 đến Giải Đặc Biệt
                        System.out.println("Giải 2: " + Giai2);
                        System.out.println("Giải 1: " + Giai1);
                        System.out.println("Giải Đặc Biệt: " + GiaiDB);
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
    }
}
