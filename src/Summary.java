import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Summary {
    // save list group data
    private final List<Group> groups;
    private final List<CovidData> covidData;
    // số ngày trong 1 nhóm
    private long interval;
    private long surplus;

    public Summary(List<CovidData> covidData) {
        this.covidData = covidData;
        this.groups = new ArrayList<>();
    }

    public void setUpSummary(Data data) {
        Scanner scanner = new Scanner(System.in);

        int number;
        do {
            System.out.println(
                    "Choose a group condition:\n" +
                            "1. No grouping.\n" +
                            "2. Number of groups.\n" +
                            "3. Number of days.\n"
            );
            number = scanner.nextInt();
            // Trong khi nhập chưa đúng thì nhập lại
        } while (number < 1 || number > 3);
        switch (number) {
            case 1:
                // Không chia nhóm, thì mỗi nhóm có 1 ngày.
                this.interval = 1;
                break;
            case 2:
                // k để tính số ngày từ ngày bắt đầu tới ngày kết thúc
                long k = ChronoUnit.DAYS.between(data.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        data.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) + 1;
                System.out.println("Enter number of groups: ");
                // option 2 là lựa chọn số nhóm, muốn tính số thành viên nhóm tối thiểu thì lấy k/số nhóm
                long numberOfGroup = scanner.nextInt();
                interval = k / numberOfGroup;
                // vì chia theo số lượng nhóm có thể ko đều. nên surplus lưu số dư ra. để phân chia lượng dư cho các nhóm
                surplus = k % numberOfGroup;
                break;
            default:
                k = ChronoUnit.DAYS.between(data.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        data.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) + 1;
                do {
                    System.out.println("Enter number of days: ");
                    interval = scanner.nextLong();
                } while (k % (interval) != 0);

        }

        do {
            System.out.println(
                    "1.New total.\n"
                            + "2.Up to.\n"
                            + "Choose result type:"
            );
            number = scanner.nextInt();
        } while (number < 1 || number > 2);
        groups.removeAll(groups);
        long sur = surplus;
        int c = 0;
        if (sur > 0) c = 1;
        for (int i = 0; i < covidData.size(); i += interval + c) {
            // lấy ra phần tử thứ i trong list data gốc
            CovidData cd = covidData.get(i);
            // Kiểm tra xem có đúng điều kiện trong
            if (data.checkInvalid(cd)) {
                Group group = new Group();
                group.setIndex(i);
                if (sur-- > 0) c = 1;
                else c = 0;

                for (int j = 0; j < interval + c; j++) {
                    CovidData cd1 = covidData.get(i + j);
                    if (data.checkInvalid(cd1)) {
                        group.covidD.add(cd1);
                    }
                }
                groups.add(group);
            }
        }
        // tính toán số liệu cho từng group
        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            // lấy phần tử cuối
            CovidData last = group.covidD.get(group.covidD.size() - 1);
            if (number == 2) {
                // nếu là chọn tổng số ca tại thời điểm đó
                group.setCases(last.getNewCases());
                group.setDeaths(last.getNewDeaths());
                group.setVaccines(last.getPeopleVaccinated());
                groups.set(i, group);
            } else {
                // nếu là chọn số ca tăng trong group đó
                // lấy số liệu ngày cuối - số liệu ngày ngay trước group
                // vì số liệu đã được cộng dồn
                CovidData pre = covidData.get(group.getIndex() - 1);
                group.setCases(last.getNewCases() - pre.getNewCases());
                group.setDeaths(last.getNewDeaths() - pre.getNewDeaths());
                group.setVaccines(last.getPeopleVaccinated() - pre.getPeopleVaccinated());
                groups.set(i, group);
            }
        }
    }

    public List<Group> getGroups() {
        return groups;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public List<CovidData> getCovidData() {
        return covidData;
    }
}