import java.util.ArrayList;
import java.util.List;

public class Group {
    List<CovidData> covidD;
    int index;
    long cases;
    long deaths;
    long vaccines;

    Group() {
        covidD = new ArrayList<>();
        this.index = 0;
    }

    @Override
    public String toString() {
        return "Group{" +
                "covidD=" + covidD +
                ", index=" + index +
                ", cases=" + cases +
                ", deaths=" + deaths +
                ", vaccines=" + vaccines +
                '}';
    }

    public List<CovidData> getCovidD() {
        return covidD;
    }

    public void setCovidD(List<CovidData> covidD) {
        this.covidD = covidD;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getCases() {
        return cases;
    }

    public void setCases(long cases) {
        this.cases = cases;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public long getVaccines() {
        return vaccines;
    }

    public void setVaccines(long vaccines) {
        this.vaccines = vaccines;
    }
}