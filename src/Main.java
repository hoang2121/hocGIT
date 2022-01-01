import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        List<CovidData> covidData = new ArrayList<>();
        try {
            //get path to project
            String userDirectory = System.getProperty("user.dir");
            // read file covid-data.csv
            BufferedReader br = new BufferedReader(new FileReader(userDirectory + "/src/covid-data.csv"));
            // read field name line in file data
            String line = br.readLine();
            //get first line data
            line = br.readLine();
            while (line != null) {
                CovidData cd = new CovidData(line.split(","));
                // nếu dữ liệu đang xét cùng iso code với hàng trước nó, thì cộng dồn để tiện cho việc tính toán
                if (covidData.size()>0 && cd.getIsoCode().equalsIgnoreCase(covidData.get(covidData.size() - 1).getIsoCode())) {
                    CovidData cd_last = covidData.get(covidData.size() - 1);
                    cd_last.setNewCases(cd.getNewCases() + cd_last.getNewCases());
                    cd_last.setNewDeaths(cd.getNewDeaths() + cd_last.getNewDeaths());
                }
                // thêm dòng dữ liệu vừa đọc được vào list
                covidData.add(cd);
                // Đọc dòng tiếp theo
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Đối tượng Data lưu trữ điều kiện về ngày bắt đầu - kết thúc và khu vực được chọn
        Data data = new Data();
        // Đối tượng lưu trữ cách chia nhóm, các nhóm sau khi chia, và dữ liệu được tính trong các nhóm
        Summary summary = new Summary(covidData);
        // Đối tượng display để tạo bảng hoặc biểu đồ
        Display display = new Display();
        // Vòng lặp để nhận các yêu cầu của người dùng
        loop:
        while (true) {
            System.out.println("1.Choose geographic area.\n" +
                    "2.Choose the time range.\n" +
                    "3.Summary.\n" +
                    "4.Show table.\n" +
                    "5.Show chart.\n" +
                    "6.Finish.\n" +
                    "Choose an option: ");
            int number = scanner.nextInt();
            switch (number) {
                case 1:
                    data.setGeographicArea(covidData);
                    break;
                case 2:
                    data.enterDate();
                    break;
                case 3:
                    summary.setUpSummary(data);
                    break;
                case 4:
                    display.showTable(summary);
                    break;
                case 5:
                    display.showChart(summary);
                    break;
                default:
                    break loop;
            }
        }
        System.out.println(data);
    }
}