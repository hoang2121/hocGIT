import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CovidData {
    private String isoCode;
    private String continent;
    private String location;
    private Date date;
    private long newCases;
    private long newDeaths;
    private long peopleVaccinated;
    private long population;

    public CovidData() {
    }

    // passing a array string is a line in data
    public CovidData(String[] raw_data) throws ParseException {
        // Truyền các thông tin tương ứng trong 1 hàng dữ liệu vào lớp này để tiện sử dụng
        this.isoCode = raw_data[0];
        this.continent = raw_data[1];
        this.location = raw_data[2];
        this.date = new SimpleDateFormat("MM/dd/yyyy").parse(raw_data[3]);

        // Trường hợp rỗng thì cho nó = 0 (theo đề)
        try{
            this.newCases = Long.parseLong(raw_data[4]);
        }catch (Exception e){
            this.newCases = 0;
        }
        try{
            this.newDeaths = Long.parseLong(raw_data[5]);
        }catch (Exception e){
            this.newDeaths = 0;
        }
        try{
            this.peopleVaccinated = Long.parseLong(raw_data[6]);
        }catch (Exception e){
            this.peopleVaccinated = 0;
        }
        try{
            this.population = Long.parseLong(raw_data[7]);
        }catch (Exception e){
            this.population = 0;
        }
    }

    public String getIsoCode() {
        return this.isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getContinent() {
        return this.continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(String date) {
        try {
            this.date = new SimpleDateFormat("MM/dd/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public long getNewCases() {
        return this.newCases;
    }

    public void setNewCases(long newCases) {
        this.newCases = newCases;
    }

    public long getNewDeaths() {
        return this.newDeaths;
    }

    public void setNewDeaths(long newDeaths) {
        this.newDeaths = newDeaths;
    }

    public long getPeopleVaccinated() {
        return this.peopleVaccinated;
    }

    public void setPeopleVaccinated(long peopleVaccinated) {
        this.peopleVaccinated = peopleVaccinated;
    }

    public long getPopulation() {
        return this.population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "DataCovid{" +
                "isoCode='" + isoCode + '\'' +
                ", continent='" + continent + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", newCases=" + newCases +
                ", newDeaths=" + newDeaths +
                ", peopleVaccinated=" + peopleVaccinated +
                ", population=" + population +
                '}';
    }
}
