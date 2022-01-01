import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Data {
    private String geographicArea;
    private Date startDate;
    private Date endDate;

    public Data(String country, Date startDate, Date endDate) {
        this.geographicArea = country;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Data() {
    }

    public boolean checkInvalid(CovidData cd){
        if((cd.getDate().equals(startDate) || cd.getDate().equals(endDate) ||
                (cd.getDate().after(startDate) && cd.getDate().before(endDate))) &&
                (cd.getIsoCode().equalsIgnoreCase(getGeographicArea()) || cd.getLocation().equalsIgnoreCase(getGeographicArea()) ||
                        cd.getContinent().equalsIgnoreCase(getGeographicArea()))
        ) return true;
        return false;
    }

    public String getGeographicArea() {
        return this.geographicArea;
    }

    public void setGeographicArea(List<CovidData> covidData) {
        do {
            Scanner k = new Scanner(System.in);
            System.out.println("\nEnter geographic area: ");
            String geographicArea = k.nextLine();
            this.geographicArea = geographicArea;
        } while (!check(covidData));

    }

    private boolean check(List<CovidData> covidData) {
        // Kiểm tra xem khu vực nhập vào có trong dữ liệu hay không
        for (int i = 0; i < covidData.size(); i++) {
            CovidData cd = covidData.get(i);
            if (cd.getContinent().equalsIgnoreCase(getGeographicArea())
                    || cd.getIsoCode().equalsIgnoreCase(getGeographicArea())
                    || cd.getLocation().equalsIgnoreCase(getGeographicArea())) {
                return true;
            }
        }
        return false;
    }

    public void enterDate() throws ParseException {
        Scanner k = new Scanner(System.in);
        System.out.println("1.A pair of start date and end date (inclusive)." +
                "\n2.A number of days or weeks from a particular date." +
                "\n3.A number of days or weeks to a particular date."
        );
        int number = k.nextInt();
        switch (number) {
            case 1:
                k.nextLine();
                System.out.println("Start date: ");
                String startDate = k.nextLine();
                System.out.println("End date: ");
                String endDate = k.nextLine();
                setStringToStartDate(startDate);
                setStringToEndDate(endDate);
                break;
            case 2:
                k.nextLine();
                System.out.println("From date: ");
                String start = k.nextLine();
                setStringToStartDate(start);
                System.out.println("Number: ");
                int dateNumber = k.nextInt();

                // format string to Date, plus dateNumber to startDate
                Date endD = new SimpleDateFormat("MM/dd/yyyy").parse(start);
                Calendar c = Calendar.getInstance();
                c.setTime(endD);
                c.add(Calendar.DATE, dateNumber);
                endD = c.getTime();

                setStringToStartDate(start);
                setEndDate(endD);
                break;
            case 3:
                k.nextLine();
                System.out.println("End date: ");
                String end = k.nextLine();
                System.out.println("Number: ");
                dateNumber = k.nextInt();
                Date startD = new SimpleDateFormat("MM/dd/yyyy").parse(end);
                c = Calendar.getInstance();
                c.setTime(startD);
                c.add(Calendar.DATE, -dateNumber);
                startD = c.getTime();
                setStartDate(startD);
                setStringToEndDate(end);
                break;
            default:
                break;
        }
        System.out.println(Data.this);
    }

    public Date getStartDate() {
        return this.startDate;
    }

    // set start Date
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    // String to Date and set startDate
    public void setStringToStartDate(String startDate) throws ParseException {
        this.startDate = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    // format string to date and set it to end date
    public void setStringToEndDate(String endDate) throws ParseException {
        this.endDate = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
    }

    @Override
    public String toString() {
        return "geographicArea='" + geographicArea + '\'' +
                ", startDate=" + new SimpleDateFormat("MM/dd/yyyy").format(startDate)+
                ", endDate=" + new SimpleDateFormat("MM/dd/yyyy").format(endDate);
    }
}