package sorasdreams.apiclima.model;

import java.util.Arrays;

public class CitiesGeocodingData {

    private CityGeocodingData[] results;

    public CityGeocodingData[] getResults() {
        return results;
    }

    public void setResults(CityGeocodingData[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "CitiesGeocodingData{" +
                "results=" + Arrays.toString(results) +
                '}';
    }
}
